package com.CMD.ui.settings;

import com.CMD.alert.AlertMaker;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Preferences {
    public static final String CONFIG_FILE = "config.txt";

    String username;
    String password;

    public Preferences(){
        username = "";
        setPassword("");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 16) {
            this.password = DigestUtils.sha1Hex(password);
        }else
            this.password = password;
    }

    public static void initConfig(){
        Writer writer = null;

        try{
            Preferences preferences = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences, writer);

        }catch(IOException ex){
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try{
                writer.close();
            }catch (IOException ex){
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public static Preferences getPreferences(){
        Gson gson = new Gson();
        Preferences preferences = new Preferences();

        try{
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException e) {
            Logger.getLogger(Preferences.class.getName()).info("Config file is missing. Creating new one with default config");
            initConfig();
        }

        return preferences;
    }

    public static void writePreferencesToFile(Preferences preference){
        Writer writer = null;
        try{
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference, writer);

            AlertMaker.showSimpleAlert("Success", "Settings updated");
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            AlertMaker.showErrorMessage( "Failed", "Cant save configuration file");
        }finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
