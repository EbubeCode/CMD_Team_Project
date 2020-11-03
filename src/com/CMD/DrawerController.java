package com.CMD;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DrawerController {
    @FXML
    private JFXButton addPaymentRecordButton, addNewMemberButton, viewMemberRecordButton,
            deleteMemberButton, updateMemRecButton, updateMemberRecord, aboutCmdButton, settingsButton;

    /*
     * Method to handle the 4 buttons on the menuBar.
     *This method calls the createStage() method to create the stage for each button pressed.
     */

    public void handleButtonPressed(ActionEvent event) throws Exception{
        if (event.getSource() == addNewMemberButton){
            createStage("ui/addNewMember.fxml");

        }else if (event.getSource() == viewMemberRecordButton){
            createStage("ui/viewMemberRecord.fxml");

        }else if (event.getSource() == addPaymentRecordButton){
            createStage("ui/addPayment.fxml");

        }else if (event.getSource() == aboutCmdButton){
            createStage("ui/aboutCMD.fxml");

        }
    }


    //    Method to create the modal Stage when a button is pressed
    static void createStage(String rootFile) throws Exception {
        Stage stage;
        Parent root;

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        root = FXMLLoader.load(MainAppPageController.class.getResource(rootFile));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        WindowStyle.allowDrag(root, stage);
        stage.show();
    }
}
