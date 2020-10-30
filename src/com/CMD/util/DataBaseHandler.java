package com.CMD.util;

import com.CMD.model.Member;
import com.CMD.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Comparator;

import static com.CMD.util.DBValues.*;

/*
 * Singleton class for querying the database
 */
public class DataBaseHandler {

    private PreparedStatement insertIntoMembers;

    private PreparedStatement insertIntoRecords;

    private PreparedStatement queryMember;

    private PreparedStatement queryNewMember;

    private PreparedStatement queryRecord;

    private PreparedStatement queryMemberRecords;

    private Connection conn;

    private ObservableList<Member> members;

    private ObservableList<Record> records;

    private static DataBaseHandler instance;

    private Statement queryMembers;




    private DataBaseHandler(){
            try {
                conn = DriverManager.getConnection(CONNECTION_STRING.value);
                Statement statement = conn.createStatement();
                statement.execute(CREATE_TABLE.value);
                statement.execute(CREATE_RECORD_TABLE.value);

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
            queryNewMember = conn.prepareStatement(QUERY_NEW_MEMBER.value);
            queryRecord = conn.prepareStatement(QUERY_RECORD_INSERT.value);
            queryMemberRecords = conn.prepareStatement(QUERY_MEMBER_RECORDS.value);

            insertIntoMembers = conn.prepareStatement(INSERT_MEMBER.value, Statement.RETURN_GENERATED_KEYS);
            insertIntoRecords = conn.prepareStatement(INSERT_RECORD.value, Statement.RETURN_GENERATED_KEYS);

            records = FXCollections.observableArrayList();

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
            if (insertIntoRecords != null){
                insertIntoRecords.close();
            }
            if (queryMember != null){
                queryMember.close();
            }
            if (queryNewMember != null){
                queryNewMember.close();
            }
            if (queryRecord != null){
                queryRecord.close();
            }
            if (queryMemberRecords != null){
                queryMemberRecords.close();
            }
            if (queryMembers != null){
                queryMembers.close();
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
        queryNewMember.setString(1, firstName);
        queryNewMember.setString(2, lastName);

        ResultSet result = queryNewMember.executeQuery();
        Member newMember = new Member(result.getInt("_id"), result.getString("fName"), result.getString("lName"),
                result.getString("phoneNumber"), result.getString("email"), result.getString("dateOfBirth"),
                result.getString("imageUrl"));
        members.add(newMember);
        members.sort(Comparator.comparing(p -> p.getFirstName().get()));
    }

    public boolean insertRecord(String amount, String month, int memberId) throws SQLException {
        queryRecord.setString(1, amount);
        queryRecord.setString(2, month);
        queryRecord.setInt(3, memberId);

        ResultSet result = queryRecord.executeQuery();
        //check if member exists in the table
        if (result.next()){
            return false;
        }else{
            insertIntoRecords.setString(1, amount);
            insertIntoRecords.setString(2, month);
            insertIntoRecords.setInt(3, memberId);
            int rows = insertIntoRecords.executeUpdate();
            if (rows != 1){
                throw new SQLException("Couldn't insertMember record!");
            }
            return true;
        }
    }

    private ResultSet queryMemberRecords(int ID) throws SQLException {
        queryMemberRecords.setInt(1, ID);

        return  queryMemberRecords.executeQuery();
    }

    public ObservableList<Record> getRecords(int Id) throws SQLException {

        records.clear();
        
        ResultSet result = queryMemberRecords(Id);
        while (result.next()) {
            Record s = new Record(result.getString(COLUMN_AMOUNT.value), result.getString(COLUMN_MONTH.value));
            records.add(s);
        }

        return records;
    }

}
