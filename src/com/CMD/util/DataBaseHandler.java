package com.CMD.util;

import java.sql.*;

/*
 * Singleton class for querying the database
 */
public class DataBaseHandler {
    private static DataBaseHandler instance;
    private String insert = "INSERT INTO members (fName, lName, phoneNumber," +
            " email, dateOfBirth, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";

    private DataBaseHandler() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlite:src/resources/dataBase/CMD.db"
        ); Statement statement = conn.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "members (fName TEXT, lName Text, " +
                    " phoneNumber Text, email TEXT, dateOfBirth Text, imageUrl TEXT)");
        } catch (SQLException e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    public void insert(String fName, String lName, String phoneNumber, String email,
                       String dateOfBirth, String imgUrl) {

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlite:src/resources/dataBase/CMD.db"
        ); PreparedStatement statement = conn.prepareStatement(insert)) {

            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, phoneNumber);
            statement.setString(4, email);
            statement.setString(5, dateOfBirth);
            statement.setString(6, imgUrl);
            statement.execute();

        } catch (SQLException e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DataBaseHandler.getInstance();
    }

    public static DataBaseHandler getInstance() {
        if (instance == null)
            instance = new DataBaseHandler();
        return instance;
    }

}
