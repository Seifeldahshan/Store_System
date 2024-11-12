package Customer;

import DataBase.DataBaseConnection;
import java.sql.*;
import java.util.Scanner;

public class Login {

    public void authenticate(Scanner scanner) {
        boolean repeat = true;

        try (Connection connection = DataBaseConnection.getConnection()) {
            while (repeat) {
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                System.out.print("Enter your password: ");
                String pass = scanner.nextLine();

                String sql = "SELECT customerpass, customername FROM customers WHERE customeremail = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, email);

                    try (ResultSet resultSet = pstmt.executeQuery()) {
                        if (resultSet.next()) {
                            String passdb = resultSet.getString("customerpass");
                            String namedb = resultSet.getString("customername");

                            if (passdb.equals(pass)) {
                                System.out.println("Welcome " + namedb.toUpperCase());
                                repeat = false;
                            } else {
                                System.out.println("Invalid password, please try again.");
                            }
                        } else {
                            System.out.println("User not found, please try again.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
