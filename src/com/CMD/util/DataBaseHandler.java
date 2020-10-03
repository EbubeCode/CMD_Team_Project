package com.CMD.util;

import java.sql.*;

public class DataBaseHandler {
    private static DataBaseHandler instance;
    private String insert = "INSERT INTO members (fName, lName, phoneNumber," +
            " email, dateOfBirth, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";

    private DataBaseHandler() {
    }

    public void insert(String fName, String lName, String phoneNumber, String email,
                       String dateOfBirth, String imgUrl) {

        try (Connection  conn = DriverManager.getConnection(
                "jdbc:sqlite:C:\\Users\\Ebube\\IdeaProjects\\CMD_PROJECT\\src\\com\\CMD\\database\\CMD.db"
        );  PreparedStatement statement = conn.prepareStatement(insert)){

            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, phoneNumber);
            statement.setString(4, email);
            statement.setString(5, dateOfBirth);
            statement.setString(6, imgUrl);
            statement.execute();

        } catch (SQLException e){
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DataBaseHandler.getInstance().insert("Chukwuma", "Ebube", "09050426383",
                "chukwuma258@gmail.com", "08/12/1997",
                "C:\\Users\\Ebube\\IdeaProjects\\CMD_PROJECT\\src\\resources\\image_res\\Ebube.png");
    }

     public static DataBaseHandler getInstance() {
        if (instance == null)
            instance = new DataBaseHandler();
        return instance;
    }

}
