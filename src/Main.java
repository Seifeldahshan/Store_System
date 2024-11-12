import Customer.Login;
import Customer.Signup;
import Service.CartImpl;
import ui.ShowShopcartData;
import ui.Show_data;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1- Login \n2- Signup\nPlease select an option:");

        int entered = 0;
        while (true) {
            try {
                entered = scanner.nextInt();
                scanner.nextLine();
                if (entered == 1 || entered == 2) break;
                else System.out.println("Invalid option. Please select 1 or 2.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        switch (entered) {
            case 1:
                Login login = new Login();
                login.authenticate(scanner);
                break;
            case 2:
                Signup signup = new Signup("asd", "asd", "asd");
                signup.SignUp();
                break;
        }

        CartImpl cart = new CartImpl();
        boolean running = true;


        while (running) {
            System.out.println("\n1: Show items");
            System.out.println("2: Add new item as an admin");
            System.out.println("3: Add an item to customer shopping cart");
            System.out.println("4: Remove an item from customer shopping cart");
            System.out.println("5: View the items in customer shopping cart");
            System.out.println("6: End shopping and go to checkout");
            System.out.println("7: Empty customer shopping cart");
            System.out.println("8: Exit the program");
            System.out.print("Please select an option: ");

            int choice = 0;
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 1 && choice <= 8) break;
                    else System.out.println("Invalid option. Please select a number between 1 and 8.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("Show items.");
                    Show_data show = new Show_data();
                    show.displayData();
                    break;
                case 2:
                    System.out.println("Add new item selected.");
                    cart.addItemAsAdmin(1);
                    break;
                case 3:
                    System.out.println("Add an item to customer shopping cart selected.");
                    cart.addItemToCart(1, 1);
                    break;
                case 4:
                    System.out.println("Remove an item from customer shopping cart selected.");
                    cart.removeItemFromCart(1);
                    break;
                case 5:
                    System.out.println("View the items in customer shopping cart selected.");
                    ShowShopcartData showShopcartData = new ShowShopcartData();
                    showShopcartData.showShopcartData();
                    break;
                case 6:
                    System.out.println("End shopping and go to checkout selected.");
                    cart.checkout();
                    break;
                case 7:
                    System.out.println("Empty customer shopping cart selected.");
                    cart.clearCart();
                    break;
                case 8:
                    System.out.println("Exiting the program...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select a number between 1 and 8.");
                    break;
            }
        }

        scanner.close();
    }
}
