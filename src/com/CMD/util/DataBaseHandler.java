package com.CMD.util;

import com.CMD.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static com.CMD.util.DBValues.*;

/*
 * Singleton class for querying the database
 */
public class DataBaseHandler {

    private PreparedStatement insertIntoMembers;

    private PreparedStatement queryMember;

    private Connection conn;

    private ObservableList<Member> members;

    private static DataBaseHandler instance = new DataBaseHandler();

    private Statement queryMembers;



    private DataBaseHandler(){
            try {
                conn = DriverManager.getConnection(CONNECTION_STRING.value);
                Statement statement = conn.createStatement();
                statement.execute(CREATE_TABLE.value);
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
            queryMember = conn.prepareStatement(QUERY_MEMBER.value);
            insertIntoMembers = conn.prepareStatement(INSERT_MEMBER.value, Statement.RETURN_GENERATED_KEYS);

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

    // Method to query all the members in the database
    private ResultSet queryAllMembers() throws SQLException {

        queryMembers = conn.createStatement();
        return queryMembers.executeQuery(QUERY_MEMBERS.value);
    }

    // Method for updating the list of members
    private void setMembers()  {
        members = FXCollections.observableArrayList();
        try {
            ResultSet result = queryAllMembers();
            while (result.next()) {
                Member newMember = new Member(result.getInt("_id"), result.getString("fName"), result.getString("lName"),
                        result.getString("phoneNumber"), result.getString("email"), result.getString("dateOfBirth"),
                        result.getString("imageUrl"));

                members.add(newMember);

            }
            queryMembers.close();
        }catch (SQLException e) {
            RequestHandler.getInstance().showAlert("Couldn't load members information from database " + e.getMessage());
        }

    }

    public ObservableList<Member> getMembers() {
        if (members == null) {
            setMembers();
        }
        return members;
    }

    public void updateMembers(String firstName, String lastName) throws SQLException {
        queryMember.setString(1, firstName);
        queryMember.setString(2, lastName);

        ResultSet result = queryMember.executeQuery();
        Member newMember = new Member(result.getInt("_id"), result.getString("fName"), result.getString("lName"),
                result.getString("phoneNumber"), result.getString("email"), result.getString("dateOfBirth"),
                result.getString("imageUrl"));
        members.add(newMember);
    }

}
