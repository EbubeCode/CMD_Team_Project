package com.CMD;

import animatefx.animation.Flash;
import animatefx.animation.ZoomIn;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class AddNewMemberController {
    @FXML
    public Label inv_data_label;

    @FXML
    private Label closeLabel;

    @FXML
    private JFXTextField fNameField, lNameField, pNumberField, emailField;

    @FXML
    private JFXDatePicker dobField;

    @FXML
    private JFXCheckBox checkBox;

    private FileChooser fileChooser;

    private String imageUrl;


    private static final String EMAIL_REGEX = "^\\w+@(gmail|yahoo).com$";

    private static final String PHONE_REGEX = "\\d{11}";


    public void initialize() {
        fileChooser = new FileChooser();
    }

    public void closeLabelPressed() {
        RequestHandler.getInstance().handleCloseLabel(closeLabel);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }


    //    Register button handler
    public void onRegister() {
        String[] fields = {fNameField.getText(), lNameField.getText(), pNumberField.getText(),
                emailField.getText(), dobField.getEditor().getText()};

        if (!(fields[0].isEmpty() && fields[1].isEmpty() && fields[2].isEmpty() && fields[3].isEmpty() &&
                fields[4].isEmpty())) {

            if (!fields[3].matches(EMAIL_REGEX)) {
                inv_data_label.setText("Invalid email address");
                inv_data_label.setTextFill(Color.valueOf("#fad859"));
                new ZoomIn(emailField).play();
                new Flash(inv_data_label).play();

            } else if (!fields[2].matches(PHONE_REGEX)) {
                inv_data_label.setText("Invalid phone number");
                inv_data_label.setTextFill(Color.valueOf("#fad859"));
                new ZoomIn(pNumberField).play();
                new Flash(inv_data_label).play();

            } else {
                Platform.runLater(() -> {
                    try {
                        boolean check = DataBaseHandler.getInstance().
                                insertMember(fields[0], fields[1], fields[2], fields[3], fields[4], imageUrl);
                        if (check) {
                            ButtonType buttonType = RequestHandler.getInstance().showAlert("New Member Successfully Added",
                                    "Success!", Alert.AlertType.CONFIRMATION);
                            if (buttonType == ButtonType.OK) {
                                Stage stage = (Stage) fNameField.getScene().getWindow();
                                stage.close();
                            }
                        } else {
                            RequestHandler.getInstance().showAlert("Member already exists in records...",
                                    "Member Check", Alert.AlertType.INFORMATION);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void onCheckBox() {
        if (checkBox.isSelected()) {
            fileChooser.setTitle("Add Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "Image Files", "*.png", "*.jpg"
            ));
            File selectedFile = fileChooser.showOpenDialog(
                    checkBox.getScene().getWindow()
            );
            if (selectedFile != null) {
               imageUrl = selectedFile.getPath();
            }
            else
                checkBox.setSelected(false);
        }
    }
}
