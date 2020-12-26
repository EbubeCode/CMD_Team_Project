/*
* Copyright (c) 2020, CMD and/or its affiliates. All rights reserved.
* CMD PROPRIETARY/CONFIDENTIAL. Use is subject to CMD license terms.
*
*
*
*
*
*
*
*/


/**
 * @author CMD_TEAM
 */


package com.CMD.ui.main;

import animatefx.animation.FadeIn;
import com.CMD.alert.AlertMaker;
import com.CMD.database.DataBaseHandler;
import com.CMD.exceptions.ExceptionUtil;
import com.CMD.ui.settings.Preferences;
import com.CMD.util.RequestAssistant;
import com.CMD.util.WindowStyle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main extends Application {

    Parent root;
    boolean isAppLocked;



    private final static Logger LOGGER = LogManager.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception{

        if(!checkWelcomeScreen()){
            root = FXMLLoader.load(getClass().getResource("/com/CMD/ui/main/main.fxml"));
        }else if (checkAppLock()){
            root = FXMLLoader.load(getClass().getResource("/com/CMD/ui/login/login.fxml"));
        }else{
            root = FXMLLoader.load(getClass().getResource("/com/CMD/ui/welcome/welcome_page.fxml"));
        }


        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        WindowStyle.allowDrag(root, primaryStage);
        primaryStage.show();

        RequestAssistant.setStageIcon(primaryStage);

/*
   *AnimateFX Library has been added from Maven.
   *Check the External Libraries folder or goto Project Structure -> Libraries.
*/
        new FadeIn(root).play();

        new Thread(() -> {
            ExceptionUtil.init();
            DataBaseHandler.getInstance();
        }).start();
    }

    //   TODO: Check if the app has been locked here using JSON data
    private boolean checkAppLock() {
        Preferences preferences = Preferences.getPreferences();

        String uname = preferences.getUsername();
        String pWord = preferences.getPassword();

        if (!uname.isEmpty() && !pWord.isEmpty()){
            isAppLocked = true;
        }else if (uname.isEmpty() && pWord.isEmpty()){
            isAppLocked = false;
        }

        return isAppLocked;
    }

    //   TODO: Check if the welcome screen has been shown here using JSON data
    private boolean checkWelcomeScreen() {
        return true;
    }

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "CMD Team Collation App launched on {}", RequestAssistant.formatDateTimeString(startTime));

        launch(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Long exitTime = System.currentTimeMillis();
            LOGGER.log(Level.INFO, "CMD Team Collation App is closing on {}. Used for {} ms", RequestAssistant.formatDateTimeString(startTime), exitTime);
        }));
    }

    @Override
    public void init() throws Exception {
        super.init();
        if(!DataBaseHandler.getInstance().open()){
            AlertMaker.getInstance().showAlert("FATAL ERROR: Couldn't connect to the database");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataBaseHandler.getInstance().close();
    }
}
