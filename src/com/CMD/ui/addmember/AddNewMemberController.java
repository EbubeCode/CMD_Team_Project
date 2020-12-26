package com.CMD.ui.addmember;

import animatefx.animation.ZoomIn;
import com.CMD.database.DataBaseHandler;
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
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class AddNewMemberController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private Label closeLabel;


    @FXML
    private JFXTextField fNameField, lNameField, pNumberField, emailField;

    @FXML
    private JFXDatePicker dobField;

    @FXML
    private JFXCheckBox checkBox;

    private String imageUrl;

    private static final String EMAIL_REGEX = "^\\w+@(gmail|yahoo).com$";

    private static final String PHONE_REGEX = "\\d{11}";


    public void closeLabelPressed() {
        AlertMaker.getInstance().handleCloseLabel(closeLabel);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }


    //    Register button handler
    public void onRegister() {
        String[] fields = {fNameField.getText(), lNameField.getText(), pNumberField.getText(),
                emailField.getText(), dobField.getEditor().getText()};

        if (fields[0].isEmpty()) {
            fNameField.setFocusColor(Color.valueOf("d91e18"));
            new ZoomIn(fNameField).play();
            fNameField.requestFocus();
        }
        else if (fields[1].isEmpty()) {
            lNameField.setFocusColor(Color.valueOf("d91e18"));
            new ZoomIn(lNameField).play();
            lNameField.requestFocus();
        }
        else {
            if (!fields[2].matches(PHONE_REGEX)) {
                pNumberField.setFocusColor(Color.valueOf("d91e18"));
                new ZoomIn(pNumberField).play();
                pNumberField.requestFocus();


            } else if (!fields[3].matches(EMAIL_REGEX)) {
                emailField.setFocusColor(Color.valueOf("d91e18"));
                new ZoomIn(emailField).play();
                emailField.requestFocus();

            } else {
                Platform.runLater(() -> {
                    try {
                        boolean check = DataBaseHandler.getInstance().
                                insertMember(fields[0], fields[1], fields[2], fields[3], fields[4], imageUrl);
                        DataBaseHandler.getInstance().updateMembers(fields[0], fields[1]);
                        if (check) {
                            JFXButton button = new JFXButton("Okay");
                            AlertMaker.showMaterialModalDialog(rootPane, Collections.singletonList(button), "Success", "New Member Successfully Added");

                            button.setOnAction(event -> {
                                Stage stage = (Stage) fNameField.getScene().getWindow();
                                stage.close();
                            });
                        } else {
                            JFXButton button = new JFXButton("Okay");
                            AlertMaker.showMaterialModalDialog(rootPane, Collections.singletonList(button), "Member Check", "Member already exists in records...");
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
        fNameField.setOnAction(event -> lNameField.requestFocus());
        lNameField.setOnAction(event -> pNumberField.requestFocus());
        pNumberField.setOnAction(event -> emailField.requestFocus());
        emailField.setOnAction(event -> dobField.requestFocus());
    }
}
