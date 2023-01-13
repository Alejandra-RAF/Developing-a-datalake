package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqliteReader implements DatabaseReader {
    //Agregamos las variables
    private LocalDate from;
    private LocalDate to;

    public SqliteReader(){
        this.from = null;
        this.to = null;
    }

    //Para obtener las fechas
    public void obtenerFechas() {
        // Nos conectamos al datamart
        String url = "jdbc:sqlite:datamart.db";
        try (Connection connection = DriverManager.getConnection(url)) {

            Class.forName("org.sqlite.JDBC");
            //Obtiene las fechas mínima y máxima de la tabla Temperatura_max
            String min_max_date = "SELECT min(date) as min_date, max(date) as max_date FROM Temperatura_max";
            PreparedStatement statement = connection.prepareStatement(min_max_date);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                from = LocalDate.parse(result.getString("min_date"));
                to = LocalDate.parse(result.getString("max_date"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public LocalDate getFrom(){
        return from;
    }

    public LocalDate getTo(){
        return to;
    }


    @Override
    public List<Event_Max> readMax(LocalDate from, LocalDate to) {

            // Nos conectamos al datamart
            String url = "jdbc:sqlite:datamart.db";

            try (Connection connection = DriverManager.getConnection(url)) {

                Class.forName("org.sqlite.JDBC");
                // Para leer las columnas de la tabla Temperatura_max de la base de datos
                String temperature_max = "SELECT time, date, station, place, value FROM Temperatura_max";
                // Hacemos la conexión con la tabla
                PreparedStatement statement_max = connection.prepareStatement(temperature_max);

                // Guardamos los datos de la tabla
                ResultSet resultSet_max = statement_max.executeQuery();
                ArrayList<Event_Max> events_max = new ArrayList<>();

                 while (resultSet_max.next()) {
                    String time = resultSet_max.getString("time");
                    String date = resultSet_max.getString("date");
                    String station = resultSet_max.getString("station");
                    String place = resultSet_max.getString("place");
                    double value = resultSet_max.getDouble("value");
                    System.out.println("time: " + time + ", date: " + date +
                        ", station: " + station + ", place: " + place +
                        ", value: " + value);
                    events_max.add(new Event_Max(time, date, station, place, value));
                }
                return events_max;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

    @Override
    public List<Event_Min> readMin(LocalDate from, LocalDate to) {
        // Nos conectamos al datamart
        String url = "jdbc:sqlite:datamart.db";

        try (Connection connection = DriverManager.getConnection(url)) {

            Class.forName("org.sqlite.JDBC");
            // Para leer las columnas de la tabla Temperatura_min de la base de datos
            String temperature_min = "SELECT time, date, station, place, value FROM Temperatura_min";
            // Hacemos la conexión con la tabla
            PreparedStatement statement_min = connection.prepareStatement(temperature_min);

            // Guardamos los datos de la tabla
            ResultSet resultSet_min = statement_min.executeQuery();
            ArrayList<Event_Min> events_min = new ArrayList<>();

            while (resultSet_min.next()) {
                String time = resultSet_min.getString("time");
                String date = resultSet_min.getString("date");
                String station = resultSet_min.getString("station");
                String place = resultSet_min.getString("place");
                double value = resultSet_min.getDouble("value");
                System.out.println("time: " + time + ", date: " + date +
                        ", station: " + station + ", place: " + place +
                        ", value: " + value);
                events_min.add(new Event_Min(time, date, station, place, value));
            }
            return events_min;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
