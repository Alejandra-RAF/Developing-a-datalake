package example;

import java.io.File;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        String datalakeRoot = args[0];
        FileDatalake datalake = new FileDatalake(new File(datalakeRoot));
        SqliteWriter datamart = new SqliteWriter();
        Controller controller = new Controller(datalake, datamart);
        controller.start();
    }
}