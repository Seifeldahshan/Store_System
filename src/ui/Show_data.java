package ui;

import DataBase.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Show_data {




    public void displayData() {
        try( Connection connection = DataBaseConnection.getConnection() ) {


            Statement stmt = connection.createStatement();


            ResultSet resultSet = stmt.executeQuery("SELECT * FROM items");


            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }

            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
