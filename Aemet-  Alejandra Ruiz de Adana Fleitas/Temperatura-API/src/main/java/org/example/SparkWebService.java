package org.example;

import spark.Spark;

public class SparkWebService {

    private TemperatureCommand maxCommand;
    private TemperatureCommand minCommand;

    public SparkWebService(TemperatureCommand maxCommand, TemperatureCommand minCommand) {
        this.maxCommand = maxCommand;
        this.minCommand = minCommand;
    }

    public void startServer() {
        Spark.port(4567);
        Spark.staticFiles.location("/public");
        Spark.get("/v1/places/with-max-temperature", maxCommand::execute_max);
        Spark.get("/v1/places/with-min-temperature", minCommand::execute_min);
    }
}
