package com.CMD.ui.updatemember;

import animatefx.animation.ZoomIn;
import com.CMD.database.DataBaseHandler;
import com.CMD.model.Member;
import com.CMD.alert.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

import java.util.Collections;
import java.util.ResourceBundle;

public class UpdateMemberController implements Initializable {
    @FXML
    public static JFXButton updateButton;

    @FXML
    private StackPane rootPane;

    @FXML
    private Label closeLabel;

    @FXML
    private JFXTextField firstNameField, lastNameField,
             phoneNumberField, emailAddressField;

    @FXML
    private JFXDatePicker  doBField;

    @FXML
    private JFXCheckBox  imageBox;

    private String updatedImageUrl;

    private static final String EMAIL_REGEX = "^\\w+@(gmail|yahoo).com$";

    private static final String PHONE_REGEX = "\\d{11}";

    private Member updateMember;


    public void closeLabelPressed() {
        AlertMaker.getInstance().handleCloseLabel(closeLabel);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            updateMember = DataBaseHandler.getInstance().getUpdateMember();


            firstNameField.setOnAction(event -> lastNameField.requestFocus());
            lastNameField.setOnAction(event -> phoneNumberField.requestFocus());
            phoneNumberField.setOnAction(event -> emailAddressField.requestFocus());
            emailAddressField.setOnAction(event -> doBField.requestFocus());

            firstNameField.setText(updateMember.getFirstName().get());
            lastNameField.setText(updateMember.getLastName().get());
            phoneNumberField.setText(updateMember.getPhoneNumber());
            emailAddressField.setText(updateMember.getEmail());
            doBField.getEditor().setText(updateMember.getDateOfBirth());
            updatedImageUrl = updateMember.getImgUrl();
        });
    }

    public void onUpdate() {

        String[] fields = {firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText(),
                emailAddressField.getText(), doBField.getEditor().getText()};

        Member member = DataBaseHandler.getInstance().getUpdateMember();
        if (member != null) {
            int id = member.getID();

            Platform.runLater(() -> {
                if (!fields[3].matches(EMAIL_REGEX)) {
                    emailAddressField.setFocusColor(Color.valueOf("d91e18"));
                    new ZoomIn(emailAddressField).play();
                    emailAddressField.requestFocus();

                } else if (!fields[2].matches(PHONE_REGEX)) {
                    phoneNumberField.setFocusColor(Color.valueOf("d91e18"));
                    new ZoomIn(phoneNumberField).play();
                    phoneNumberField.requestFocus();

                }else {
                    Boolean success = DataBaseHandler.getInstance().updateMember(fields[0], fields[1], fields[2], fields[3], fields[4],
                            updatedImageUrl, id, updateMember);

                    if(success) {
                        member.setFirstName(fields[0]);
                        member.setLastName(fields[1]);
                        member.setPhoneNumber(fields[2]);
                        member.setEmail(fields[3]);
                        member.setDateOfBirth(fields[4]);
                        member.setImgUrl(updatedImageUrl);


                        JFXButton button = new JFXButton("Okay");
                        AlertMaker.showMaterialModalDialog(rootPane, Collections.singletonList(button), "Success", "Update Completed");

                        button.setOnAction(event -> {
                            Stage stage = (Stage) firstNameField.getScene().getWindow();
                            stage.close();
                        });
                    } else {
                        JFXButton button = new JFXButton("Okay");
                        AlertMaker.showMaterialModalDialog(rootPane, Collections.singletonList(button),
                                "Member Check", "A different member already has these names...");
                    }

                }
            });

        } else {
            JFXButton button = new JFXButton("Okay");
            AlertMaker.showMaterialModalDialog(rootPane, Collections.singletonList(button), "Member Check", "No member was selected...");
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
