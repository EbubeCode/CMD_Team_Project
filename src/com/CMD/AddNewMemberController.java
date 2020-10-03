package com.CMD;

import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddNewMemberController {
    @FXML
    private Label closeLabel;

    @FXML
    private JFXTextField fNameField, lNameField, pNumberField, emailField;

    @FXML
    private JFXDatePicker dobField;


    public void closeLabelPressed(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleCloseLabel(closeLabel);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

    public void onRegister(ActionEvent event) {
        String[] fields = {fNameField.getText(), lNameField.getText(), pNumberField.getText(),
                emailField.getText(), dobField.getEditor().getText()};
        if (!(fields[0].isEmpty() && fields[1].isEmpty() && fields[2].isEmpty() && fields[3].isEmpty() &&
                fields[4].isEmpty())) {

            Platform.runLater(() -> DataBaseHandler.getInstance().
                    insert(fields[0], fields[1], fields[2], fields[3], fields[4], null));
            Stage stage = (Stage) fNameField.getScene().getWindow();
            stage.close();
        }
    }

}
