package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static  String url = "jdbc:mysql://127.0.0.1:3306/Store";
    private static String username = "root";
    private static String password = "asterqwdv";
    public static  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
