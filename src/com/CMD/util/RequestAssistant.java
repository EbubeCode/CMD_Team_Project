package com.CMD.util;

import com.CMD.alert.AlertMaker;
import com.CMD.data.callback.GenericCallback;
import com.CMD.export.pdf.ListToPdf;
import com.CMD.model.Record;
import com.CMD.ui.main.MainAppPageController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class RequestAssistant {

    private static final String ICON_IMAGE_LOC = "/resources/icons/icon.png";
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");


//    Method to initialize pdf export. Calls the ListToPdf.doPrintToPdf() method
public static void initPDFExport(StackPane rootPane, Node contentPane, Stage stage, List<Record> data, GenericCallback callback) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save as PDF");
    FileChooser.ExtensionFilter extFilter
            = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
    fileChooser.getExtensionFilters().add(extFilter);
    File saveLoc = fileChooser.showSaveDialog(stage);
    ListToPdf ltp = new ListToPdf();
    boolean flag = ltp.doPrintToPdf(data, saveLoc, ListToPdf.Orientation.LANDSCAPE);
    JFXButton okayBtn = new JFXButton("Okay");
    JFXButton openBtn = new JFXButton("View File");
    openBtn.setOnAction((ActionEvent event1) -> {
        Platform.runLater(() -> {
            try {
                Desktop.getDesktop().open(saveLoc);
            } catch (Exception exp) {
                AlertMaker.showErrorMessage("Could not load file", "Cant load file");

            }
        });
    });
    if (flag) {
        AlertMaker.showMaterialDialog(rootPane, contentPane, Arrays.asList(okayBtn, openBtn), "Completed",
                "Member data has been exported.");
        callback.taskCompleted(null);
    } else {
        AlertMaker.showMaterialDialog(rootPane, contentPane, Arrays.asList(okayBtn), "Error",
                "File couldn't be saved. Check if it is open in another application");
        callback.taskCompleted(null);
    }
}


    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(MainAppPageController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }

    public static String formatDateTimeString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String formatDateTimeString(Long time) {
        return DATE_TIME_FORMAT.format(new Date(time));
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

//    Method to validate email address using better regex format
    public static boolean validateEmailAddress(String emailID) {
//        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
          String regex = "^[A-Za-z0-9]+([-_\\.\\+][A-Za-z0-9]+)*@"
                  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }
}
