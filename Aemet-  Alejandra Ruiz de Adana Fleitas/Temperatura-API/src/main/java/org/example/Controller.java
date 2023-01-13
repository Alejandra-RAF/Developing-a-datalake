package org.example;

import java.time.LocalDate;
import java.util.List;

public class Controller {

    public void run() {
        SqliteReader reader = new SqliteReader();
        reader.obtenerFechas(); // obtiene las fechas del datamart
        LocalDate from = reader.getFrom();
        LocalDate to = reader.getTo();
        List<Event_Max> maxEvents = reader.readMax(from, to);
        List<Event_Min> minEvents = reader.readMin(from, to);


        TemperatureCommand maxCommand = new TemperatureCommand(reader);
        TemperatureCommand minCommand = new TemperatureCommand(reader);
        SparkWebService webService = new SparkWebService( maxCommand, minCommand);
        webService.startServer();
    }
}
