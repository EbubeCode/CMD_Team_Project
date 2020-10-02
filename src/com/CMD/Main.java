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
import javafx.application.Application;
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
}
