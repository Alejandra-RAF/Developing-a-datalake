package org.example;

import java.time.LocalDate;
import java.util.List;

public interface DatabaseReader {

    List<Event_Max> readMax(LocalDate from, LocalDate to);

    List<Event_Min> readMin(LocalDate from, LocalDate to);

    LocalDate getFrom();

    LocalDate getTo();
}
