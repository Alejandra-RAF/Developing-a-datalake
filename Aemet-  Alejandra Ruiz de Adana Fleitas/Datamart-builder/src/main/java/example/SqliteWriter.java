package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteWriter extends DatabaseWriter {
    private final Connection connection;


    public SqliteWriter() throws SQLException {
        String url = "jdbc:sqlite:datamart.db";
        connection = DriverManager.getConnection(url);
        initDatabase();
    }


    private static final String TABLA_TEMP_MAX =
            "CREATE TABLE IF NOT EXISTS Temperatura_max(" +
                    "date TEXT, " +
                    "time TEXT, " +
                    "station TEXT, " +
                    "place TEXT, " +
                    "value TEXT);";

    private static final String TABLA_TEMP_MIN =
            "CREATE TABLE IF NOT EXISTS Temperatura_min(" +
                    "date TEXT, " +
                    "time TEXT, " +
                    "station TEXT, " +
                    "place TEXT, " +
                    "value TEXT);";

    private void initDatabase() throws SQLException {
        connection.createStatement().execute(TABLA_TEMP_MAX);
        connection.createStatement().execute(TABLA_TEMP_MIN);

    }

    @Override
    public void addmin(Event minEventtoday, Event minEventyesterday) {
        try {
            String insertMin = "INSERT INTO Temperatura_min(date, time, station, place, value) VALUES('%s', '%s', '%s', '%s', '%.1f')";
            String sql1 = String.format(insertMin, minEventtoday.date, minEventtoday.time, minEventtoday.idema, minEventtoday.place, minEventtoday.tamin);
            connection.createStatement().execute(sql1);
            String sql2 = String.format(insertMin, minEventyesterday.date, minEventyesterday.time, minEventyesterday.idema, minEventyesterday.place, minEventyesterday.tamin);
            connection.createStatement().execute(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addmax(Event maxEventtoday, Event maxEventyesterday) {
        try {
            String insertMax = "INSERT INTO Temperatura_max(date, time, station, place, value) VALUES('%s', '%s', '%s', '%s', '%.1f')";
            String sql1 = String.format(insertMax, maxEventtoday.date, maxEventtoday.time, maxEventtoday.idema, maxEventtoday.place, maxEventtoday.tamax);
            connection.createStatement().execute(sql1);
            String sql2 = String.format(insertMax, maxEventyesterday.date, maxEventyesterday.time, maxEventyesterday.idema, maxEventyesterday.place, maxEventyesterday.tamax);
            connection.createStatement().execute(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete() throws SQLException {
        // Borra todos los datos existentes en la tabla de eventos
        String deleteSql = "DELETE FROM Temperatura_max";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
        deleteStmt.executeUpdate();
        deleteSql = "DELETE FROM Temperatura_min";
        deleteStmt = connection.prepareStatement(deleteSql);
        deleteStmt.executeUpdate();
    }
}
