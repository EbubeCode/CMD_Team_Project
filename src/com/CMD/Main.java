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
package com.CMD;

import animatefx.animation.FadeIn;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/welcomePage.fxml"));

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        if(!DataBaseHandler.getInstance().open()){
            RequestHandler.getInstance().showAlert("FATAL ERROR: Couldn't connect to the database");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataBaseHandler.getInstance().close();
    }
}
