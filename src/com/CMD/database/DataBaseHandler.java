package com.CMD.database;

import com.CMD.model.Member;
import com.CMD.model.Record;
import com.CMD.alert.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import static com.CMD.database.DBValues.*;

/*
 * Singleton class for querying the database
*/
public class DataBaseHandler {

    private final static Logger LOGGER = LogManager.getLogger(DataBaseHandler.class.getName());

    private PreparedStatement insertIntoMembers;

    private PreparedStatement insertIntoRecords;

    private PreparedStatement queryMember;

    private PreparedStatement queryNewMember;

    private PreparedStatement queryRecord;

    private PreparedStatement queryMemberRecords;

    private Connection conn;

    private ObservableList<Member> members;

    private static DataBaseHandler instance;

    private Statement queryMembers;

    private Statement queryMembersRecords;

    private List<Member> newMembers;

    private Member updateMember;



    private DataBaseHandler(){
            try {
                conn = DriverManager.getConnection(CONNECTION_STRING.value);
                Statement statement = conn.createStatement();
                statement.execute(CREATE_TABLE.value);
                statement.execute(CREATE_RECORD_TABLE.value);
                statement.execute(CREATE_MAIL_SERVER_INFO_TABLE.value);

            }catch (SQLException e){
                LOGGER.log(Level.ERROR, "{}", e);
            }

    }


    public static DataBaseHandler getInstance() {
        if (instance == null)
            instance = new DataBaseHandler();
        return instance;
    }


    public Connection getConnection() {
        return conn;
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
            if (queryMembersRecords != null){
                queryMembersRecords.close();
            }
            if (conn != null){
                conn.close();
            }
        }catch (SQLException e){
            AlertMaker.getInstance().showAlert("Couldn't close connection: " + e.getMessage());
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
            AlertMaker.getInstance().showAlert("Couldn't load members information from database " + e.getMessage());
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
        setNewMember(newMember);
    }



    public boolean insertRecord(String amount, String month, int memberId, String detail, int year) throws SQLException {
        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        queryRecord.setString(1, amount);
        queryRecord.setString(2, month);
        queryRecord.setInt(3, memberId);
        queryRecord.setInt(4, year);


        ResultSet result = queryRecord.executeQuery();
        //check if member exists in the table
        if (result.next()){
            return false;
        }else{
            insertIntoRecords.setString(1, amount);
            insertIntoRecords.setString(2, month);
            insertIntoRecords.setInt(3, memberId);
            insertIntoRecords.setInt(4, year);
            insertIntoRecords.setString(5, detail);
            int rows = insertIntoRecords.executeUpdate();
            if (rows != 1){
                throw new SQLException("Couldn't insertMember record!");
            }
            return true;
        }
    }
    public boolean insertRecord(String amount, String month, String detail, int year) throws SQLException {
        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

            insertIntoRecords.setString(1, amount);
            insertIntoRecords.setString(2, month);
            insertIntoRecords.setInt(3, -1);
            insertIntoRecords.setInt(4, year);
            insertIntoRecords.setString(5, detail);
            int rows = insertIntoRecords.executeUpdate();
            if (rows != 1){
                throw new SQLException("Couldn't insertMember record!");
            }
            return true;
    }

    private ResultSet queryMemberRecords(int ID) throws SQLException {
        queryMemberRecords.setInt(1, ID);

        return  queryMemberRecords.executeQuery();
    }

    public ObservableList<Record> getRecords(int Id) throws SQLException {

        ObservableList<Record> records = FXCollections.observableArrayList();
        ResultSet result = queryMemberRecords(Id);
        while (result.next()) {
            Record s = new Record(result.getString(COLUMN_AMOUNT.value), result.getString(COLUMN_MONTH.value),
                    0, null, null, null);
            records.add(s);
        }

        return records;
    }

    public List<Member> getNewMembers() {
        List<Member> members = newMembers;
        newMembers = null;
        return members;
    }

    public void setNewMember(Member newMember) {
        if (newMembers == null)
            newMembers = new ArrayList<>();
        newMembers.add(newMember);
    }

    //    Task class to handle getting all the members in the database
    public static class GetAllMembersTask extends Task {

        @Override
        public ObservableList<Member> call()  {
            return FXCollections.observableArrayList
                    (DataBaseHandler.getInstance().getMembers());
        }
   }

   // Method to update first name of a member
    public Boolean updateMember(String firstName, String lastName, String phoneNumber, String email, String dob, String imageUrl,
                                int id, Member updateMember) {
        try(PreparedStatement statement = conn.prepareStatement(UPDATE_MEMBER.value)) {
            queryMember.setString(1, firstName);
            queryMember.setString(2, lastName);

            ResultSet result = queryMember.executeQuery();
            //check if member exists in the table
            if (result.next()){
                if(result.getInt(COLUMN_ID.value) != updateMember.getID())
                    return false;
            }else {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, phoneNumber);
                statement.setString(4, email);
                statement.setString(5, dob);
                statement.setString(6, imageUrl);
                statement.setInt(7, id);
                statement.execute();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "{}", e);
        }
        return true;
    }


    public void deleteMember(Member member) {
        try(PreparedStatement statement = conn.prepareStatement(DELETE_MEMBER.value)) {
            statement.setInt(1, member.getID());
            statement.execute();

            members.remove(member);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "{}", e);
        }
    }

    private ResultSet queryAllMembersRecords() throws SQLException {
        queryMembersRecords = conn.createStatement();
        return queryMembersRecords.executeQuery(QUERY_MEMBERS_RECORDS.value);
    }

   private ResultSet queryAllTeamExpenses() throws SQLException {
        queryMembersRecords = conn.createStatement();
        return queryMembersRecords.executeQuery(QUERY_EXPENSES.value);
    }

   public List<Record> getAllMembersRecords() {
        List<Record> records = new ArrayList<>();
        try {
            ResultSet result = queryAllMembersRecords();
            while(result.next()) {
                Record record = new Record(result.getString(COLUMN_AMOUNT.value), result.getString(COLUMN_MONTH.value),
                        result.getInt(COLUMN_YEAR.value), result.getString(COLUMN_FIRST_NAME.value),
                        result.getString(COLUMN_LAST_NAME.value), result.getString(COLUMN_DETAILS.value));
                records.add(record);
            }
        result = queryAllTeamExpenses();
            while(result.next()) {
                Record record = new Record(result.getString(COLUMN_AMOUNT.value), result.getString(COLUMN_MONTH.value),
                        result.getInt(COLUMN_YEAR.value), "Treasury",
                        "", result.getString(COLUMN_DETAILS.value));
                records.add(record);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "{}", e);
        }
        return records;
   }

    public Member getUpdateMember() {
        return updateMember;
    }

    public void setUpdateMember(Member updateMember) {
        this.updateMember = updateMember;
    }
}
