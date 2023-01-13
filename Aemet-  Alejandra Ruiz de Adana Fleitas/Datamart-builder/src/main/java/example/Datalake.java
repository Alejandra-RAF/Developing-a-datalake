package example;

import java.util.List;

public interface Datalake {
    List<Event> getEventsYesterday();
    List<Event> getEventsToday();
}
