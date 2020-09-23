package CMD;

/* Copyright (c) 2020, CMD and/or its affiliates. All rights reserved.
 * CMD PROPRIETARY/CONFIDENTIAL. Use is subject to CMD license terms.
*/

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.util.Optional;

public class WelcomePageController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label welcome_label;

    @FXML
    private Label motto_label;

    @FXML
    private Button proceed_button;

    @FXML
    private Label close_label;





    /* This method will be called when the close_label Label has been clicked.
     * It will create an Alert dialog, which we confirm the user's choice.
     */
    @FXML
    public void handleCloseLabel() {
        Alert alert = new Alert( Alert.AlertType.NONE,"Are you sure you want to exit?",
                ButtonType.YES,  ButtonType.CANCEL);
        alert.setTitle("Exit");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.YES)) {
            Platform.exit();
        }

    }

    /*
     *This method will be called when the proceed_button has been pressed.
     * It will call the call the Stage of the welcome_page and pass new scene to it.
     * The new scene will contain the main_app_page.
     */

    @FXML
    public void proceedButtonPressed() throws Exception {
        Stage primaryStage = (Stage) proceed_button.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("mainAppPage.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
