package CMD.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

/*
 *This is our RequestHandler Singleton that will handle close, minimize and "moving to a new Stage".
 *These tasks will be done here so as to abstract the codes from the UI controllers.
*/

public class RequestHandler {

    private static RequestHandler mInstance;

    private String defaultColorLabel_X;

    private Alert alert;

//    Singleton Constructor
    public static RequestHandler getInstance(){
        if (mInstance == null) {
            mInstance = new RequestHandler();
        }
        return mInstance;
    }


    /*
     * A private constructor to ensure that an instance of this class cannot be created
     * outside this class
     */
    private RequestHandler () {

        /*
         * create the alert object to handle onAction close
         */
        alert = new Alert( Alert.AlertType.NONE,"Are you sure you want to exit?",
                ButtonType.YES,  ButtonType.CANCEL);
        alert.setTitle("Exit");
    }


    /*
     * Method to show Alert on close
     * This method returns a ButtonType pressed so as to work on our proceed button animation effectively.
     * This is because we do not want our proceed button to stop animating when we press CANCEL on close.
     */
    public ButtonType handleClose(){
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
}
