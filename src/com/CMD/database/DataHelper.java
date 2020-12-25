package com.CMD.database;

import com.CMD.data.model.MailServerInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHelper {

    private final static Logger LOGGER = LogManager.getLogger(DataBaseHandler.class.getName());

    public static MailServerInfo loadMailServerInfo(){
        try{
            String checkStmt = "SELECT * FROM " + DBValues.TABLE_MAIL_SERVER_INFO.value;
            PreparedStatement stmt = DataBaseHandler.getInstance().getConnection().prepareStatement(checkStmt);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                String mailServer  = rs.getString("server_name");
                Integer port = rs.getInt("server_port");
                String emailID = rs.getString("user_email");
                String userPass = rs.getString("user_password");
                Boolean sslEnabled = rs.getBoolean("ssl_enabled");

                return new MailServerInfo(mailServer, port, emailID, userPass, sslEnabled);
            }
        }catch(SQLException ex){
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return null;
    }

    public static boolean updateMailServerInfo(MailServerInfo mailServerInfo){
        try{
            wipeTable("mail_server_info");
            PreparedStatement stmt = DataBaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO MAIL_SERVER_INFO(server_name, server_port, user_email, user_password, ssl_enabled) VALUES(?,?,?,?,?)");

            stmt.setString(1, mailServerInfo.getMailServer());
            stmt.setInt(2, mailServerInfo.getPort());
            stmt.setString(3, mailServerInfo.getEmailID());
            stmt.setString(4, mailServerInfo.getEncryptedPassword());
            stmt.setBoolean(5, mailServerInfo.getSslEnabled());

            return stmt.executeUpdate() > 0;
        }catch(SQLException ex){
            LOGGER.log(Level.ERROR, "{}", ex);
        }
        return false;
    }

    public static void wipeTable(String tableName) {
        try {
            Statement statement = DataBaseHandler.getInstance().getConnection().createStatement();
            statement.execute("DELETE FROM " + tableName + " WHERE TRUE");
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }
}
