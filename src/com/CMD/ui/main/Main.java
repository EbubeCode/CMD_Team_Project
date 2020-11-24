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
*
*
*
*/
package com.CMD.ui.main;

import animatefx.animation.FadeIn;
import com.CMD.util.WindowStyle;
import com.CMD.database.DataBaseHandler;
import com.CMD.alert.AlertMaker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    Parent root;
    boolean isWelcomeShown;

    @Override
    public void start(Stage primaryStage) throws Exception{
        isWelcomeShown = checkWelcomeScreen();
        if(!isWelcomeShown){
            root = FXMLLoader.load(getClass().getResource("/com/CMD/ui/main/main.fxml"));
        }else{
            root = FXMLLoader.load(getClass().getResource("/com/CMD/ui/welcome/welcome_page.fxml"));
        }


        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        WindowStyle.allowDrag(root, primaryStage);
        primaryStage.show();

/*
   *AnimateFX Library has been added from Maven.
   *Check the External Libraries folder or goto Project Structure -> Libraries.
*/
        new FadeIn(root).play();
    }

//   TODO: Check if the welcome screen has been shown here using JSON data
    private boolean checkWelcomeScreen() {

        return true;
    }

    public static void main(String[] args) {
        launch(args);
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
