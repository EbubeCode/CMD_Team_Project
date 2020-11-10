package com.CMD;

import animatefx.animation.Flash;
import animatefx.animation.ZoomIn;
import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewMemberController implements Initializable {

    @FXML
    public Label inv_data_label, inv_data_label_U;

    @FXML
    private Label closeLabel;
    @FXML
    private Label editProfile;

    @FXML
    private JFXTextField fNameField, lNameField, pNumberField, emailField, 
            firstNameField, lastNameField, phoneNumberField, emailAddressField;

    @FXML
    private JFXDatePicker dobField, doBField;

    @FXML
    private JFXCheckBox checkBox, imageBox;

    private String imageUrl, updatedImageUrl;

    private static final String EMAIL_REGEX = "^\\w+@(gmail|yahoo).com$";

    private static final String PHONE_REGEX = "\\d{11}";


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
                emailField.requestFocus();

            } else if (!fields[2].matches(PHONE_REGEX)) {
                inv_data_label.setText("Invalid phone number");
                inv_data_label.setTextFill(Color.valueOf("#fad859"));
                new ZoomIn(pNumberField).play();
                new Flash(inv_data_label).play();
                pNumberField.requestFocus();

            } else {
                Platform.runLater(() -> {
                    try {
                        boolean check = DataBaseHandler.getInstance().
                                insertMember(fields[0], fields[1], fields[2], fields[3], fields[4], imageUrl);
                        DataBaseHandler.getInstance().updateMembers(fields[0], fields[1]);
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

    /*
     * Method to add the image path for a new member
    */
    public void onCheckBox() {
        if (checkBox.isSelected()) {
            FileChooser fileChooser = new FileChooser();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Member member = DataBaseHandler.getInstance().getUpdateMember();
            editProfile.setText("Edit Profile for: " + member.getFirstName().get() + " " + member.getLastName().get());
        });
    }

    public void onUpdate(ActionEvent actionEvent) {

        String[] fields = {firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText(),
                emailAddressField.getText(), doBField.getEditor().getText()};

        Member member = DataBaseHandler.getInstance().getUpdateMember();
        if (member != null) {
            int id = member.getID();

            Platform.runLater(() -> {
                boolean updated = false;
                if(!fields[0].isEmpty()){
                    DataBaseHandler.getInstance().updateFirstName(fields[0], id);
                    member.setFirstName(fields[0]);
                    updated = true;
                }
                if(!fields[1].isEmpty()){
                    DataBaseHandler.getInstance().updateLastName(fields[1], id);
                    member.setLastName(fields[1]);
                    updated = true;
                }
                if (fields[2].matches(PHONE_REGEX)) {
                    DataBaseHandler.getInstance().updatePhoneNumber(fields[2], id);
                    member.setPhoneNumber(fields[2]);
                    updated = true;

                }
                if (fields[3].matches(EMAIL_REGEX)) {
                    DataBaseHandler.getInstance().updateEmail(fields[3], id);
                    member.setEmail(fields[3]);
                    updated = true;

                }
                if(!fields[4].isEmpty()){
                    DataBaseHandler.getInstance().updateDOB(fields[4], id);
                    member.setDateOfBirth(fields[4]);
                    updated = true;
                }
                if (updatedImageUrl != null) {
                    DataBaseHandler.getInstance().updateImageUrl(updatedImageUrl, id);
                    member.setImgUrl(updatedImageUrl);
                    updatedImageUrl = null;
                    updated = true;
                }
                if (updated) {
                    ButtonType buttonType = RequestHandler.getInstance().showAlert("Member details updated successfully",
                            "Success!", Alert.AlertType.CONFIRMATION);
                    if (buttonType == ButtonType.OK) {
                        Stage stage = (Stage) firstNameField.getScene().getWindow();
                        stage.close();
                    }
                } else {
                    inv_data_label_U.setText("No new updates detected");
                    inv_data_label_U.setTextFill(Color.valueOf("#fad859"));
                    new ZoomIn(firstNameField).play();
                    new Flash(inv_data_label_U).play();
                    firstNameField.requestFocus();
                }

            });
        } else {
            RequestHandler.getInstance().showAlert("No member was selected...",
                    "Member Check", Alert.AlertType.INFORMATION);
        }
    }

    public void onImageBox(){
        if (imageBox.isSelected()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Update Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "Image Files", "*.png", "*.jpg"
            ));
            File selectedFile = fileChooser.showOpenDialog(
                    imageBox.getScene().getWindow()
            );
            if (selectedFile != null) {
                updatedImageUrl = selectedFile.getPath();
            }
            else
                imageBox.setSelected(false);
        }
    }
}
