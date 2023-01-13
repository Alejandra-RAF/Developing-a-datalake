package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class TemperatureCommand {
    private DatabaseReader reader;
    private Gson gson;


    public TemperatureCommand(DatabaseReader reader) {
        this.reader = reader;
        this.gson = new Gson();
    }


    public Object execute_max(Request request, Response response) {
        Map<String, String[]> parameters = request.queryMap().toMap();
        LocalDate from = reader.getFrom();
        LocalDate to = reader.getTo();
        List<Event_Max> events_max = reader.readMax(from,to);
        String jsonResponse = gson.toJson(events_max);
        response.status(200);
        response.type("application/json");
        return jsonResponse;
        }


    public Object execute_min(Request request, Response response) {
        Map<String, String[]> parameters = request.queryMap().toMap();
        LocalDate from = reader.getFrom();
        LocalDate to = reader.getTo();
        List<Event_Min> events_min = reader.readMin(from,to);
        String jsonResponse = gson.toJson(events_min);
        response.status(200);
        response.type("application/json");
        return jsonResponse;
    }
}
