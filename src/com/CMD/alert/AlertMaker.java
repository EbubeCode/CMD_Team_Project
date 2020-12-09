package com.CMD.alert;

import com.CMD.util.RequestAssistant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Optional;

/*
 *This is our RequestHandler Singleton that will handle close, minimize and "moving to a new Stage".
 *These tasks will be done here so as to abstract the codes from the UI controllers.
*/

public class AlertMaker {

    private static AlertMaker mInstance;

    private String defaultColorLabel_X;

    private final Alert  alert;


//    Singleton Constructor
    public static AlertMaker getInstance(){
        if (mInstance == null) {
            mInstance = new AlertMaker();
        }
        return mInstance;
    }


    /*
     * A private constructor to ensure that an instance of this class cannot be created
     * outside this class
    */
    private AlertMaker(){
//    Create the alert object to handle onAction close
        alert = new Alert(Alert.AlertType.NONE,"Are you sure you want to exit?",
                ButtonType.YES,  ButtonType.CANCEL);
        alert.setTitle("Exit");
    }

    public static void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }


    //  Method to handle close due to Modality of the stage containing the close_label.
    public void handleCloseLabel(Label label){
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }

    public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<JFXButton> controls, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);


        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("dialog-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
                    dialog.close()
            );
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialogLayout.getStylesheets().add(AlertMaker.class.getResource("/com/CMD/util/style_util/dark-theme.css").toExternalForm());
        dialogLayout.getStyleClass().add("custom-alert");
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) ->{
                    if(nodeToBeBlurred != null)
                        nodeToBeBlurred.setEffect(null);
        }
        );
        if(nodeToBeBlurred != null)
            nodeToBeBlurred.setEffect(blur);
    }

    public static void showMaterialDialog(StackPane root, List<JFXButton> controls, String header, String body) {
        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);


        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("dialog-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
                    dialog.close()
            );
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialogLayout.getStylesheets().add(AlertMaker.class.getResource("/com/CMD/util/style_util/dark-theme.css").toExternalForm());
        dialogLayout.getStyleClass().add("custom-alert");
        dialog.show();
    }

    public static void showMaterialModalDialog(StackPane root, List<JFXButton> controls, String header, String body) {
        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setPrefWidth(200);
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);


        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("dialog-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
                    dialog.close()
            );
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialogLayout.getStylesheets().add(AlertMaker.class.getResource("/com/CMD/util/style_util/dark-theme.css").toExternalForm());
        dialogLayout.getStyleClass().add("custom-alert");
        dialog.show();
    }


    /*
     * Method to show Alert on close
     * This method returns a ButtonType pressed so as to work on our proceed button animation effectively.
     * This is because we do not want our proceed button to stop animating when we press CANCEL on close.
    */
    public ButtonType handleClose(){

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog");

        ButtonType buttonType = null;
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.YES)) {
            buttonType = result.get();
            Platform.exit();
        }else if(result.isPresent() && (result.get() == ButtonType.CANCEL)){
            buttonType = result.get();
        }
        return buttonType;
    }


    /*
     * Method to change the background color of the close and minimize button when
     * a mouse enters the Label
   */
    public void handleMouseEntered(Label closeLabel) {
        defaultColorLabel_X = closeLabel.getStyle();
        closeLabel.setStyle("-fx-background-color: #d91e18");
    }

    /*
     * Method to change the background color of the close and minimize button when
     * a mouse leaves the Label to default color of the Label
     */
    public void handleMouseExited(Label closeLabel) {
        if(defaultColorLabel_X != null) {
            closeLabel.setStyle(defaultColorLabel_X);
        }
    }

//    Method to handle minimize
    public void handleMinimize(Stage stage){
        if(stage != null){
            stage.setIconified(true);
        }
    }



    public void showAlert(String message) {
        Alert alert = new Alert( Alert.AlertType.ERROR, message,
                ButtonType.OK);
        alert.setTitle("Error!");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog");
        alert.showAndWait();
        alert.initStyle(StageStyle.UNDECORATED);
    }

    public ButtonType showAlert(String message, String title, Alert.AlertType type) {
        ButtonType buttonType = null;
        Alert alert = new Alert( type, message,
                ButtonType.OK);
        alert.setTitle(title);
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/com/CMD/util/style_util/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            buttonType = result.get();
            alert.close();
        }
        return buttonType;
    }

    public ButtonType showAlertOption(String message, String title, Alert.AlertType type) {
        ButtonType buttonType = null;
        Alert alert = new Alert( type, message,
                ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            buttonType = result.get();
            alert.close();
        }
        return buttonType;
    }

    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        RequestAssistant.setStageIcon(stage);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/com/CMD/util/style_util/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("normal-alert");
    }
}
