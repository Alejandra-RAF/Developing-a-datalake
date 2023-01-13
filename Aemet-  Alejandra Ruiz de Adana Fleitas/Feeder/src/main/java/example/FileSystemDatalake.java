package example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class FileSystemDatalake implements Datalake{

    private final File datalakeRoot;
    LocalDate today = LocalDate.now(); //Para ver el momento actual de ejecucion del programa (Hoy)
    LocalDate yesterday = today.minusDays(1); //Para tener el dia anterior (Ayer)

    //Creamos el datalake, el cual se crea en la ruta que tú le especifiques con el nombre que tú le pongas
    //Ejemplo: C:\Users\Usuario\Desktop\datalake
    public FileSystemDatalake(File datalakeRoot) {
        this.datalakeRoot = datalakeRoot;
        if (!this.datalakeRoot.mkdirs()){
            System.out.println("Se ha creado correctamente");
        }else{
            System.out.println("No hay archivo");
        }
    }

    @Override //para leer la hora del ultimo evento registrado en el dia actual de la ejecucion
    public int readtoday(LocalDate date, File filetoday) throws IOException {
        List<String> totalhours = new ArrayList<>();
        String[] event;
        int lasthour;
        try (BufferedReader reader = new BufferedReader(new FileReader(filetoday))) {
            String line;
            while ((line = reader.readLine()) != null) {
                event = line.split("\n");
                for (String Event : event) {
                    String hour = Event.substring(18, 20);
                    totalhours.add(hour);
                }
            }
            int lastline = totalhours.size() - 1;
            lasthour = Integer.parseInt(totalhours.get(lastline));
        }
        return lasthour;
    }

    @Override //para leer la hora del ultimo evento registrado en el dia previo a la ejecucion
    public int readyesterday(LocalDate date, File fileyesterday) throws IOException {
        List<String> totalhours = new ArrayList<>();
        String[] event;
        int lasthour;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileyesterday))) {
            String line;
            while ((line = reader.readLine()) != null) {
                event = line.split("\n");
                for (String Event : event) {
                    String hour = Event.substring(18, 20);
                    totalhours.add(hour);
                }
            }
            int lastline = totalhours.size() - 1;
            lasthour = Integer.parseInt(totalhours.get(lastline));
        }
        return lasthour;
    }

    //Para escribir los eventos
    @Override
    public void store(LocalDate date, List<Event> events) {
        System.out.println("CREANDO EVENTOS...");
        //Patra el instant
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Gson gson = builder.create();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filenametoday = today.format(formatter) + ".events";
        String filenameyesterday = yesterday.format(formatter) + ".events";
        File filetoday = new File(datalakeRoot, filenametoday);
        File fileyesterday = new File(datalakeRoot, filenameyesterday);

        //Para que la primera vez de la ejecucion no se queden sin nigun valor inicializamos las variables
        int latestTimestamptoday = 0;
        int latestTimestampyesterday = 0;
        try {
            latestTimestamptoday = readtoday(date, filetoday);
            latestTimestampyesterday = readyesterday(date, fileyesterday);
        } catch (IOException e) {
        } finally { //para que siga la ejecucion del programa porque la primera vez no existira ningun evento en los ficheros .events
            for (Event event : events) {

                //Para sacar la hora y el dia del evento
                ZoneId zone = ZoneId.of("Atlantic/Canary"); //Para ubicar su zona horaria
                Instant ts = event.getTs(); //Para conseguir el timestamp del evento

                LocalTime time = ts.atZone(zone).toLocalTime(); //Para extraer la hora convirtiendola a un localdate
                int hour = time.getHour();

                LocalDate timeasdate = ts.atZone(zone).toLocalDate();//Para extraer el dia convirtiendolo a un localdate
                int day = timeasdate.getDayOfMonth();

                if(hour > latestTimestampyesterday && day < today.getDayOfMonth()) { //esta funcion se utiliza para cuando lee una fecha
                    try {
                        //El dia de ejecucion de programa
                        int actualday = today.getDayOfMonth();


                        // Escribe los datos en json
                        String json = gson.toJson(event);

                        if (actualday == day) { //Mira en que fichero vamos a escribir el evento segun su dia
                            FileWriter writertoday = new FileWriter(filetoday, true);
                            writertoday.write(json + "\n");
                            writertoday.close();
                        } else {
                            FileWriter writeryesterday = new FileWriter(fileyesterday, true);
                            writeryesterday.write(json + "\n");
                            writeryesterday.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    if (hour > latestTimestamptoday && day >= today.getDayOfMonth()) {
                        try {
                            //El dia de ejecucion de programa
                            int actualday = today.getDayOfMonth();

                            //Para sacar el dia del evento
                            Instant eventtime = event.getTs(); //Para conseguir el timestamp del evento
                            ZoneId zoneId = ZoneId.of("Atlantic/Canary"); //Para ubicar su zona horaria
                            LocalDate localDate = eventtime.atZone(zoneId).toLocalDate(); //Para extraer el día convirtiendola a un localdate
                            int eventday = localDate.getDayOfMonth();

                            // Escribe los datos en json
                            String json = gson.toJson(event);


                            if (actualday == eventday) { //esta condicion valorara en que fichero escribir el evento segun su dia
                                FileWriter writertoday = new FileWriter(filetoday, true);
                                writertoday.write(json + "\n");
                                writertoday.close();
                            } else {
                                FileWriter writeryesterday = new FileWriter(fileyesterday, true);
                                writeryesterday.write(json + "\n");
                                writeryesterday.close();
                            }
                        } catch (IOException e) {
                            System.out.println("Error al escribir en el archivo");
                        }
                    }
                }
            }
        }
    }
}












