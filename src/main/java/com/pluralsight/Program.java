package com.pluralsight;

import java.sql.*;
import java.util.Scanner;
import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
<<<<<<< HEAD
        String password = "*****";
        String query = "SELECT * FROM Products";
=======
        String password = "*******";
//        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE ProductID = ?";
        String query1 = "SELECT * FROM Products";
        String query2 = "SELECT * FROM Customers";
        String query3 = "SELECT * FROM Categories ORDER BY CategoryID";
>>>>>>> 9df870c (first)


 Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to do?");
        System.out.println("1) Display all products");
        System.out.println("2) Display all customers");
        System.out.println("3) Display all categories");
        System.out.println("0) Display all customers");
        int userOption = scanner.nextInt();

        switch (userOption){
            case 1: {
                getProducts(url, user, password, query1);
            }break;
            case 2: {getCustomers(url, user, password, query2);}
            break;
            case 3: displayAll(url,user,password,query3,scanner);
            break;
        }







    }

    private static void getProducts(String url, String user, String password, String query1) {
        Connection conn = null;
        ResultSet rs = null;
        try {
            //trying to get a connection
            conn = DriverManager.getConnection(url, user, password);

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
        try (     //trying to get a connection
                  Connection conn = DriverManager.getConnection(url, user, password);

                  //passing in the preparedStatement
                  PreparedStatement preparedStatement = conn.prepareStatement(query2);

                  //setting the first parameter of the statement to a value
//            preparedStatement.setInt(1,10);


//            Statement stmt = conn.createStatement();

                  //Executes the passed in query
                  ResultSet rs = preparedStatement.executeQuery();){


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