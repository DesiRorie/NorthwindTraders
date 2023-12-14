package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.util.Scanner;
import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";

        String myPassword = System.getenv("MY_DB_PASSWORD");


        String password = "*****";


//        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE ProductID = ?";
        String query1 = "SELECT * FROM Products";
        String query2 = "SELECT * FROM Customers";
        String query3 = "SELECT * FROM Categories ORDER BY CategoryID";


        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to do?");
        System.out.println("1) Display all products");
        System.out.println("2) Display all customers");
        System.out.println("3) Display all categories");
        System.out.println("4) Add new shipper data and phone");
        System.out.println("5) Update shipper phone number");
        System.out.println("6) Delete a shipper based using shipperID");
        System.out.println("0) Display all customers");
        int userOption = scanner.nextInt();

        switch (userOption) {
            case 1: {
                getProducts(url, user, myPassword, query1);
            }
            break;
            case 2: {
                getCustomers(url, user, myPassword, query2);
            }
            break;
            case 3:
                displayAll(url, user, myPassword, query3, scanner);
                break;
            case 4: {
                addAndDisplayShippers(scanner, user, myPassword);
            }
            case 5: {
                updateShipperNumber(scanner, user, myPassword);
            }
            case 6: {
                deleteShipperById(scanner, user, myPassword);
            }
        }
    }

    private static void deleteShipperById(Scanner scanner, String user, String myPassword) {
        System.out.println("Enter the shipperID of the shipper to delete.");
        int shipperID = scanner.nextInt();
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(user);
        dataSource.setPassword(myPassword);

        try (
                Connection conn = dataSource.getConnection();

                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM Shippers WHERE ShipperID = ?");
                PreparedStatement preparedStatement2 = conn.prepareStatement("SELECT * FROM Shippers");
                ResultSet rs2 = preparedStatement2.executeQuery();
        ) {
            preparedStatement.setInt(1,shipperID);
            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows deleted %d\n", rows);
            while (rs2.next()) {
                //getting the values and setting the variables to use later.
                int shipperIDs = rs2.getInt("ShipperID");
                String companyName = rs2.getString("CompanyName");
                String phone2 = rs2.getString("Phone");

                System.out.println("ShipperID: " + shipperIDs + " CompanyName: " + companyName + " Phone: " + phone2 );

                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateShipperNumber(Scanner scanner, String user, String myPassword) {
        System.out.println("To change the phone number of the shipper enter the id of the shipper to change");
        int chosenID = scanner.nextInt();
        System.out.println("What is the new number?");
        String newNumber = scanner.next();
        scanner.nextLine();
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(user);
        dataSource.setPassword(myPassword);

        try (
                Connection conn = dataSource.getConnection();

                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Shippers SET Phone = ? \" +\n" + "\"WHERE ShipperID = ?");

                PreparedStatement preparedStatement2 = conn.prepareStatement("SELECT * FROM Shippers");
                ResultSet rs2 = preparedStatement2.executeQuery();

        ) {
//preparedStatements
            preparedStatement.setString(1,newNumber);
            preparedStatement.setInt(2,chosenID);

            int rows = preparedStatement.executeUpdate();
            // Display the number of rows that were updated
            System.out.printf("Rows updated %d\n", rows);

            while (rs2.next()) {
                //getting the values and setting the variables to use later.
                int shipperID = rs2.getInt("ShipperID");
                String companyName = rs2.getString("CompanyName");
                String phone2 = rs2.getString("Phone");

                System.out.println("ShipperID: " + shipperID + " CompanyName: " + companyName + " Phone: " + phone2 );

                System.out.println();
            }
//
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addAndDisplayShippers(Scanner scanner, String user, String myPassword) {
        System.out.println("What is the name");
        String name = scanner.next();
        System.out.println("What is the phone number");
        String phone = scanner.next();
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(user);
        dataSource.setPassword(myPassword);

        try (
                Connection conn = dataSource.getConnection();
                //trying to get a connection

                //passing in the preparedStatement
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");

                PreparedStatement preparedStatement2 = conn.prepareStatement("SELECT * FROM Shippers");
                ResultSet rs2 = preparedStatement2.executeQuery();
        )




        //Executes the passed in query

        {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            int rows  = preparedStatement.executeUpdate();
            System.out.printf("Rows updated %d\n", rows);


            while (rs2.next()) {
                //getting the values and setting the variables to use later.
                int shipperID = rs2.getInt("ShipperID");
                String companyName = rs2.getString("CompanyName");
                String phone2 = rs2.getString("Phone");

                System.out.println("ShipperID: " + shipperID + " CompanyName: " + companyName + " Phone: " + phone2 );

                System.out.println();
            }
//
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getProducts(String url, String user, String password, String query1) {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        Connection conn = null;
        ResultSet rs = null;

        try {
            //trying to get a connection
            conn = dataSource.getConnection();

            //passing in the preparedStatement
            PreparedStatement preparedStatement = conn.prepareStatement(query1);

            //setting the first parameter of the statement to a value
//            preparedStatement.setInt(1,10);


//            Statement stmt = conn.createStatement();

            //Executes the passed in query
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                //getting the values and setting the variables to use later.
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                double unitPrice = rs.getDouble("UnitPrice");
                int unitsInStock = rs.getInt("UnitsInStock");

                System.out.println("ProductID: " + productID);
                System.out.println("ProductName: " + productName);
                System.out.println("UnitPrice: " + unitPrice);
                System.out.println("UnitsInStock: " + unitsInStock);
                System.out.println();
            }
            //closing resultSet and connection
            rs.close();
//            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (rs != null) {
                    rs.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private static void getCustomers(String url, String user, String password, String query2) {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(user);
        dataSource.setPassword(password);


//        ResultSet rs = null;
        try (
                Connection conn = dataSource.getConnection();
                //trying to get a connection

                //passing in the preparedStatement
                PreparedStatement preparedStatement = conn.prepareStatement(query2);

                //setting the first parameter of the statement to a value
//            preparedStatement.setInt(1,10);


//            Statement stmt = conn.createStatement();

                //Executes the passed in query
                ResultSet rs = preparedStatement.executeQuery();)
        {


            while (rs.next()) {
                //getting the values and setting the variables to use later.
                String customerID = rs.getString("CustomerID");
                String companyName = rs.getString("CompanyName");
                String contactName  = rs.getString("ContactName");
                String contactTitle = rs.getString("ContactTitle");
                String address = rs.getString("Address");
                String city= rs.getString("City");
                String postalCode = rs.getString("PostalCode");
                String country = rs.getString("Country");
                String phone = rs.getString("Phone");


                System.out.println("CustomerID: " + customerID);
                System.out.println("Company Name: " + companyName);
                System.out.println("Contact Name:" + contactName);
                System.out.println("Contact Title: " + contactTitle);
                System.out.println("Address: " + address);
                System.out.println("City:" + city);
                System.out.println("Phone: " + phone);



                System.out.println();
            }
            //closing resultSet and connection
            rs.close();
//            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void displayAll(String url, String user, String password, String query3, Scanner scanner) {

        try (     //trying to get a connection
                  Connection conn = DriverManager.getConnection(url, user, password);
                  //passing in the preparedStatement
                  PreparedStatement preparedStatement = conn.prepareStatement(query3);
                  //setting the first parameter of the statement to a value
//            preparedStatement.setInt(1,10);
//            Statement stmt = conn.createStatement();
                  //Executes the passed in query
                  ResultSet rs = preparedStatement.executeQuery();
        )
        {
            while (rs.next()) {

                //getting the values and setting the variables to use later.
                int categoryID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                System.out.println();

                System.out.println("CategoryID: " + categoryID);
                System.out.println("CategoryName: " + categoryName);
            }
            System.out.println("Which category ID do you want to display");
            int chosenID = scanner.nextInt();

            String query4 = "SELECT * FROM PRODUCTS WHERE CategoryID = ? ";
            PreparedStatement preparedStatement2 = conn.prepareStatement(query4);
            preparedStatement2.setInt(1,chosenID);
            ResultSet rs2 = preparedStatement2.executeQuery();

            while(rs2.next()){
                int productID = rs2.getInt("ProductID");
                String productName = rs2.getString("ProductName");
                double unitPrice = rs2.getDouble("UnitPrice");
                int unitsInStock = rs2.getInt("UnitsInStock");

                System.out.println("ProductID: " + productID);
                System.out.println("ProductName: " + productName);
                System.out.println("UnitPrice: " + unitPrice);
                System.out.println("UnitsInStock: " + unitsInStock);
                System.out.println();
                System.out.println("------------------");
            }

            //closing resultSet and connection
            rs.close();
//            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
