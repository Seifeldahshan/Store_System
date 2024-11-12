package Customer;

import DataBase.DataBaseConnection;
import Customer.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Signup {
    private String cusname, email, pass;

    public Signup(String name, String ema, String pass) {
        this.cusname = name;
        this.email = ema;
        this.pass = pass;
    }

    public void SignUp() {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DataBaseConnection.getConnection()) {

            System.out.print("Enter your name: ");
            cusname = scanner.nextLine();


            System.out.print("Enter your email address: ");
            email = scanner.nextLine();


            System.out.print("Enter your password: ");
            pass = scanner.nextLine();

            boolean repeat = false;
            while (repeat == false) {
                if (cusname == null || cusname.isEmpty()){
                    System.out.print("Enter your name");
                    cusname = scanner.nextLine();
                } else if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")) {
                    System.out.print("Enter your email: ");
                    email = scanner.nextLine();
                } else if (pass == null || pass.isEmpty() || pass.length() < 8) {
                    System.out.print("Enter your password: ");
                    pass = scanner.nextLine();
                } else {
                    repeat = true;
                }
            }
            String sql = "INSERT INTO customers (customername , customeremail , customerpass) VALUES (? , ? , ? )";


            PreparedStatement pstmt = connection.prepareStatement(sql);
            {


                pstmt.setString(1, cusname);
                pstmt.setString(2, email);
                pstmt.setString(3, pass);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("you have signed up successfully! return to login page.");
                Login login = new Login();
                login.authenticate(scanner);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }


    }
}

