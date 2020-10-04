package com.CMD.util;

import java.sql.*;

/*
 * Singleton class for querying the database
 */
public class DataBaseHandler {
//    Constants
    public static final String DB_NAME = "CMD.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:src/resources/dataBase/" + DB_NAME;

    public static final String TABLE_MEMBERS = "members";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "fName";
    public static final String COLUMN_LAST_NAME = "lName";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    public static final String COLUMN_EMAIL_ADDRESS = "email";
    public static final String COLUMN_BIRTH_DATE = "dateOfBirth";
    public static final String COLUMN_IMAGE_URL = "imageUrl";


    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MEMBERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, "
            + COLUMN_PHONE_NUMBER + " TEXT, " + COLUMN_EMAIL_ADDRESS + " TEXT, "
            + COLUMN_BIRTH_DATE + " TEXT, " + COLUMN_IMAGE_URL + " TEXT)";


    public static final String INSERT_MEMBER = "INSERT INTO " + TABLE_MEMBERS
            + "(" + COLUMN_FIRST_NAME + ", " + COLUMN_LAST_NAME + ", " + COLUMN_PHONE_NUMBER
            + ", " + COLUMN_EMAIL_ADDRESS + ", " + COLUMN_BIRTH_DATE + ", " + COLUMN_IMAGE_URL + ")"
            + " VALUES (?, ?, ?, ?, ?, ?)";

    public static final String QUERY_MEMBER = "SELECT " + COLUMN_ID + " FROM "
            + TABLE_MEMBERS + " WHERE " + COLUMN_FIRST_NAME + " = ? " +
            "AND " + COLUMN_LAST_NAME + " = ?";


    private PreparedStatement insertIntoMembers;

    private PreparedStatement queryMember;

    private Connection conn;

    private static DataBaseHandler instance = new DataBaseHandler();



    private DataBaseHandler(){
            try {
                conn = DriverManager.getConnection(CONNECTION_STRING);
                Statement statement = conn.createStatement();
                statement.execute(CREATE_TABLE);
            }catch (SQLException e){
                RequestHandler.getInstance().showAlert("Something went wrong " + e.getMessage());
            }
    }


    public static DataBaseHandler getInstance() {
        if (instance == null)
            instance = new DataBaseHandler();
        return instance;
    }



//    Method to connect to the database
    public boolean open(){
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryMember = conn.prepareStatement(QUERY_MEMBER);
            insertIntoMembers = conn.prepareStatement(INSERT_MEMBER, Statement.RETURN_GENERATED_KEYS);

            return true;
        }catch (SQLException e){
            return false;
        }
    }


//    Method to close the database and it's resources
    public void close(){
        try {
            if (insertIntoMembers != null){
                insertIntoMembers.close();
            }
            if (queryMember != null){
                queryMember.close();
            }
            if (conn != null){
                conn.close();
            }
        }catch (SQLException e){
            RequestHandler.getInstance().showAlert("Couldn't close connection: " + e.getMessage());
        }
    }


//    Method to insertMember data into the database
    public boolean insertMember(String fName, String lName, String phoneNumber, String email,
                                String dateOfBirth, String imgUrl) throws SQLException {
        queryMember.setString(1, fName);
        queryMember.setString(2, lName);

        ResultSet result = queryMember.executeQuery();
        //check if member exists in the table
        if (result.next()){
            return false;
        }else{
            insertIntoMembers.setString(1, fName);
            insertIntoMembers.setString(2, lName);
            insertIntoMembers.setString(3, phoneNumber);
            insertIntoMembers.setString(4, email);
            insertIntoMembers.setString(5, dateOfBirth);
            insertIntoMembers.setString(6, imgUrl);
            int rows = insertIntoMembers.executeUpdate();
            if (rows != 1){
                throw new SQLException("Couldn't insertMember member!");
            }
            return true;
        }
    }
}
