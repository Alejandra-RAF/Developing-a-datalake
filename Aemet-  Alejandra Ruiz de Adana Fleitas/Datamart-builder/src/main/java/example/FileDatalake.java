package example;
import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileDatalake implements Datalake {

    private final File datalakeRoot;

    public FileDatalake(File datalakeRoot) {
        this.datalakeRoot = datalakeRoot;
    }


    @Override
        public List<Event> getEventsToday() {
            List<Event> eventstoday = new ArrayList<>();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String filenametoday = today.format(formatter) + ".events";

            String[] event;
            try (BufferedReader reader = new BufferedReader(new FileReader(datalakeRoot + "\\" + filenametoday))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    event = line.split("\n");
                    for (String Event : event) {
                        Gson gson = new Gson();
                        Event events = gson.fromJson(Event, Event.class);
                        events.setDate(events.getTs()); //Separaremos la fecha del evento
                        events.setTime(events.getTs()); //Separaremos la hora del evento
                        eventstoday.add(events);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return eventstoday;
        }

        @Override
        public List<Event> getEventsYesterday() {
            List<Event> eventsyesterday = new ArrayList<>();
            LocalDate yesterday = LocalDate.now().minusDays(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String filenameyesterday = yesterday.format(formatter) + ".events";

            String[] event;
            try (BufferedReader reader = new BufferedReader(new FileReader(datalakeRoot + "\\" + filenameyesterday))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    event = line.split("\n");
                    for (String Event : event) {
                        Gson gson = new Gson();
                        Event events = gson.fromJson(Event, Event.class);
                        events.setDate(events.getTs()); //Separaremos la fecha del evento
                        events.setTime(events.getTs()); //Separaremos la hora del evento
                        eventsyesterday.add(events);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return eventsyesterday;
        }


}

