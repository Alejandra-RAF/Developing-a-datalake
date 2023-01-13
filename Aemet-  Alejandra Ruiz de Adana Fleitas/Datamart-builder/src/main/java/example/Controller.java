package example;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Calendar.HOUR;

public class Controller {
    public Datalake datalake;
    public SqliteWriter datamart;
    private TimerTask task;

    public Controller(FileDatalake datalake, SqliteWriter datamart) {
        this.datalake = datalake;
        this.datamart = datamart;
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    datamart.delete();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                List<Event> eventstoday = datalake.getEventsToday();
                List<Event> eventsyesterday = datalake.getEventsYesterday();

                //Averiguamos el evento con la temperatura minima mas baja
                Event minEventtoday = null;
                Event minEventyesterday = null;

                for (Event i : eventstoday) {
                    if (i.getTamin() != 0.0) { //Un sensor de temperatura de la aemet no devuelve la temperatura por lo que hacemos una condición para que mire los que sean diferentes 0.0
                        if (minEventtoday == null || i.getTamin() < minEventtoday.getTamin()) {
                            minEventtoday = i;
                        }
                    }
                }
                System.out.println("Evento HOY: " + minEventtoday + " con la temperatura mínima: " + minEventtoday.getTamin());

                for (Event i : eventsyesterday) {
                    if (i.getTamin() != 0.0) { //Un sensor de temperatura de la aemet no devuelve la temperatura por lo que hacemos una condición para que mire los que sean diferentes 0.0
                        if (minEventyesterday == null || i.getTamin() < minEventyesterday.getTamin()) {
                            minEventyesterday = i;
                        }
                    }
                }
                System.out.println("Evento AYER : " + minEventyesterday + " con la temperatura mínima: " + minEventyesterday.getTamin());
                datamart.addmin( minEventtoday, minEventyesterday);


                //Averiguamos el evento con la temperatura minima mas alta
                Event maxEventtoday = null;
                Event maxEventyesterday = null;

                for(Event i :eventstoday) {
                    if (maxEventtoday == null || i.getTamax() > maxEventtoday.getTamax()) {
                        maxEventtoday = i;
                    }
                }
                System.out.println("El evento HOY: "+maxEventtoday +" con la temperatura máxima: "+maxEventtoday.getTamax());

                for(Event i :eventsyesterday) {
                    if (maxEventyesterday == null || i.getTamax() > maxEventyesterday.getTamax()) {
                        maxEventyesterday = i;
                    }
                }
                System.out.println("El evento AYER: "+maxEventyesterday +" con la temperatura máxima: "+maxEventyesterday.getTamax());
                datamart.addmax(maxEventtoday, maxEventyesterday);

            }
        };
    }

    public void start() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, HOUR*60000*60);
    }
}