package CMD.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/*
 *This is our RequestHandler Singleton that will handle close, minimize and "moving to a new Stage".
 *These tasks will be done here so as to abstract the codes from the UI controllers.
*/

public class RequestHandler {

    private static RequestHandler mInstance;

//    Singleton Constructor
    public static RequestHandler getInstance(){
        if (mInstance == null) {
            mInstance = new RequestHandler();
        }
        return mInstance;
    }

//    Method to show Alert on close
    public void handleClose(){
        Alert alert = new Alert( Alert.AlertType.NONE,"Are you sure you want to exit?",
                ButtonType.YES,  ButtonType.CANCEL);
        alert.setTitle("Exit");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.YES)) {
            Platform.exit();
        }
    }

//    Method to handle minimize
    public void handleMinimize(Stage stage){
        if(stage != null){
            stage.setIconified(true);
        }
    }
}
