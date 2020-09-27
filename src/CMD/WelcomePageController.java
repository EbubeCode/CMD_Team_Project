package CMD;

import CMD.controller.RequestHandler;

import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/*
  *Implemented the Initializable Interface so as to override the initialize() method
*/
public class WelcomePageController implements Initializable {

    private RunThread runThread;

    @FXML
    private Button proceed_button;

    @FXML
    private ImageView proceed_imageView;

    @FXML
    private Label close_label;

    /*
     *This method will be called when the close_label Label has been clicked.
     * It will create an Alert dialog, which we confirm the user's choice.
     */
    @FXML
    public void handleCloseLabel() {
        ButtonType buttonType = RequestHandler.getInstance().handleClose();
        if (buttonType == ButtonType.YES){
            runThread.stop();
        }
    }

    /*
     *This method will be called when the proceed_button has been pressed.
     * It will call the Stage of the welcome_page and pass new scene to it.
     * The new scene will contain the main_app_page.
     */
    @FXML
    public void proceedButtonPressed() throws Exception {
        Stage primaryStage = (Stage) proceed_button.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("mainAppPage.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        runThread.stop();
        primaryStage.show();
    }

    /*
     *This is the method we override from Initializable interface
     * This method is where we set our animation for our welcome Label using animateFX
     * We also set other customizations here eg: Cursor type as shown below.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        close_label.setCursor(Cursor.HAND);
        proceed_imageView.setCursor(Cursor.HAND);


//      Using the inner class(RunThread) object to run the thread
        runThread = new RunThread(2000);
        runThread.start();
    }

    @FXML
    public void handleMouseEntered() {
        RequestHandler.getInstance().handleMouseEntered(close_label);
    }

    @FXML
    public void handleMouseExited() {
        RequestHandler.getInstance().handleMouseExited(close_label);
    }


//      Inner class to handle welcome_label on a separate thread.
     class RunThread implements Runnable {
        private final AtomicBoolean threadInterrupted = new AtomicBoolean(false);
        private int interval;
        private Thread thread;

        public RunThread(int sleepInterval) {
            interval = sleepInterval;
        }

        public void start(){
            thread = new Thread(this);
            thread.start();
        }

        public void stop(){
            threadInterrupted.set(false);
            thread.interrupt();
        }


        public void run() {
            threadInterrupted.set(true);
            while (threadInterrupted.get()) {
                try {
                    Thread.sleep(interval);
                }catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
                }
                new RubberBand(proceed_imageView).play();
            }
        }
    }
}