package Service;

import java.sql.*;
import java.util.Scanner;
import DataBase.DataBaseConnection;
import ui.Show_data;

public class CartImpl implements  CartService {


    @Override
    public void addItemToCart(int itemNums, int quantity) {
        Scanner scanner = new Scanner(System.in);
        try(Connection connection = DataBaseConnection.getConnection()) {


            System.out.print("Enter item ID to add to your shopping cart: ");
            Show_data show_data = new Show_data();
            show_data.displayData();
            String itemNum = scanner.nextLine();
            boolean repeat = false;
            while (!repeat) {
                if (itemNum == null || itemNum.isEmpty()) {
                    System.out.print("Enter item ID to add to your shopping cart: ");
                    itemNum = scanner.nextLine();
                } else {
                    repeat = true;
                }
            }
            System.out.print("Enter quantity to add to your shopping cart: ");
            String quantityStr = scanner.nextLine();
            while (quantityStr == null || quantityStr.isEmpty() || !quantityStr.matches("\\d+")) {
                System.out.print("Invalid quantity. Please enter a valid quantity: ");
                quantityStr = scanner.nextLine();
            }
            int inputQuantity = Integer.parseInt(quantityStr);


            String selectSql = "SELECT itemscol FROM items WHERE iditems = ?";
            String insertSql = "INSERT INTO shopping_cart (shopping_cartcol, quantity) VALUES (?, ?)";


            PreparedStatement selectStmt = connection.prepareStatement(selectSql);
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);


            selectStmt.setString(1, itemNum);


            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {

                String itemscolValue = rs.getString("itemscol");


                insertStmt.setString(1, itemscolValue);
                insertStmt.setInt(2, inputQuantity);


                int rowsAffected = insertStmt.executeUpdate();
                System.out.println("Item inserted into shopping_cart successfully.");
            } else {
                System.out.println("Item not found in items table.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

    }

    @Override
    public void removeItemFromCart(int itemId) {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DataBaseConnection.getConnection()) {
            System.out.print("Enter item ID to remove from your shopping cart: ");
            String itemNum = scanner.nextLine();

            boolean repeat = false;
            while (!repeat) {
                if (itemNum == null || itemNum.isEmpty()) {
                    System.out.print("Please enter a valid item ID: ");
                    itemNum = scanner.nextLine();
                } else {
                    repeat = true;
                }
            }


            String deleteSql = "DELETE FROM shopping_cart WHERE idshopping_cart = ?";

            try (PreparedStatement stmt = connection.prepareStatement(deleteSql)) {

                stmt.setInt(1, Integer.parseInt(itemNum));


                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Item removed from your shopping cart.");
                } else {
                    System.out.println("No item found with that ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for item ID. Please enter a valid integer.");
        }
    }

    @Override
    public void checkout() {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Are you ready to checkout? (yes/no): ");
            String YorN = scanner.nextLine();
            boolean repeat = false;
            while (!repeat) {
                if (YorN == null || YorN.isEmpty()) {
                    System.out.print("Please enter a Yes or No: ");
                    YorN = scanner.nextLine();
                } else {
                    repeat = true;
                }
            }

            Boolean delete = false;
            if (YorN.equals("yes")) {
                String sql = "TRUNCATE TABLE shopping_cart";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                    System.out.println("Checkout successful! Thank you for your purchase.");
                } catch (SQLException e) {
                    System.out.println("Error during checkout: " + e.getMessage());
                }
            } else {
                System.out.println("Checkout unsuccessful. Please try again.");
            }

            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addItemAsAdmin(int itemNums) {
        try (Connection connection = DataBaseConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            String AdminPass = "ADMIN123";
            System.out.println("Enter Admin Password: ");
            String EnteredPass = scanner.nextLine();

            if(!EnteredPass.equals(AdminPass)) {
                System.out.println("Access denied: incorrect admin password.");
                scanner.close();
                return;
            }



            // Get item name from user
            System.out.print("Enter item name: ");
            String itemName = scanner.nextLine();
            boolean repeat = false;
            while (repeat == false) {
                if (itemName == null || itemName.isEmpty()) {
                    System.out.print("Enter item name");
                    itemName = scanner.nextLine();
                } else {
                    repeat = true;
                }
            }
            String sql = "INSERT INTO items (itemscol) VALUES (?)";


            PreparedStatement pstmt = connection.prepareStatement(sql);


            pstmt.setString(1, itemName);


            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Item inserted successfully");

        }catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void clearCart() {
        try( Connection connection =DataBaseConnection.getConnection()){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Are you sure to empty your shopping cart? (yes/no): ");
            String YorN = scanner.nextLine();
            boolean repeat = false;
            while (!repeat) {
                if (YorN == null || YorN.isEmpty()) {
                    System.out.print("Please enter a Yes or No: ");
                    YorN = scanner.nextLine();
                } else {
                    repeat = true;
                }
            }
            Boolean delete = false;
            if (YorN.equals("yes")) {
                String sql = "TRUNCATE TABLE shopping_cart";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                    System.out.println("Shopping cart deleted successful!");
                } catch (SQLException e) {
                    System.out.println("Error during deleting: " + e.getMessage());
                }
            } else {
                System.out.println(" unsuccessful. Please try again.");
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




