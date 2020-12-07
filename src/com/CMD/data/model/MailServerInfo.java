package com.CMD.data.model;


import org.apache.commons.codec.digest.DigestUtils;

// javax.mail library was added in the libs section, this class utilizes the UI model of the MailServer tabPane in the Settings page
public class MailServerInfo {
    private String mailServer;
    private Integer port;
    private String emailID;
    private String password;
    private Boolean sslEnabled;

    public MailServerInfo(String mailServer, Integer port, String emailID, String password, Boolean sslEnabled) {
        this.mailServer = mailServer;
        this.port = port;
        this.emailID = emailID;
        setPassword(password);
        this.sslEnabled = sslEnabled;
    }

    public String getMailServer() {
        return mailServer;
    }

    public Integer getPort() {
        return port;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setPassword(String password) {
        if (password.length() < 16) {
            this.password = DigestUtils.shaHex(password);
        }else
            this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }

    @Override
    public String toString() {
        return String.format("%s:%d @ %s", mailServer, port, emailID);
    }

    public boolean validate(){
        boolean flag = mailServer == null || mailServer.isEmpty()
                || port == null || emailID == null || password.isEmpty();

        return !flag;
    }
}
