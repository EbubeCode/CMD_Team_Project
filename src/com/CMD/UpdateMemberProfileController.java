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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateMemberProfileController implements Initializable {
    @FXML
    public Label inv_data_label_U;

    @FXML
    private Label closeLabel;

    @FXML
    private Label editProfile;

    @FXML
    private JFXTextField firstNameField, lastNameField, phoneNumberField, emailAddressField;

    @FXML
    private JFXDatePicker  doBField;

    @FXML
    private JFXCheckBox  imageBox;

    private String updatedImageUrl;

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Member member = DataBaseHandler.getInstance().getUpdateMember();
            firstNameField.setText(member.getFirstName().get());
            lastNameField.setText(member.getLastName().get());
            phoneNumberField.setText(member.getPhoneNumber());
            emailAddressField.setText(member.getEmail());
            doBField.getEditor().setText(member.getDateOfBirth());
            updatedImageUrl = member.getImgUrl();
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
                if (!fields[3].matches(EMAIL_REGEX)) {
                    inv_data_label_U.setText("Invalid email address");
                    inv_data_label_U.setTextFill(Color.valueOf("#fad859"));
                    new ZoomIn(emailAddressField).play();
                    new Flash(inv_data_label_U).play();
                    emailAddressField.requestFocus();

                } else if (!fields[2].matches(PHONE_REGEX)) {
                    inv_data_label_U.setText("Invalid phone number");
                    inv_data_label_U.setTextFill(Color.valueOf("#fad859"));
                    new ZoomIn(phoneNumberField).play();
                    new Flash(inv_data_label_U).play();
                    phoneNumberField.requestFocus();

                } else {
                    DataBaseHandler.getInstance().updateMember(fields[0], fields[1], fields[2], fields[3], fields[4], updatedImageUrl, id);

                    member.setFirstName(fields[0]);
                    member.setLastName(fields[1]);
                    member.setPhoneNumber(fields[2]);
                    member.setEmail(fields[3]);
                    member.setDateOfBirth(fields[4]);
                    member.setImgUrl(updatedImageUrl);

                    ButtonType buttonType = RequestHandler.getInstance().showAlert("Member details updated successfully",
                            "Success!", Alert.AlertType.CONFIRMATION);
                    if (buttonType == ButtonType.OK) {
                        Stage stage = (Stage) firstNameField.getScene().getWindow();
                        stage.close();
                    }
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
