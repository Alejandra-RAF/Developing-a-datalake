package example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface Datalake {
    int readtoday(LocalDate date, File filetoday) throws IOException;
    int readyesterday(LocalDate date, File fileyesterday) throws IOException;

    void store(LocalDate date, List<Event> events);

}
