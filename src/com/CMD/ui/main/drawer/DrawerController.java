package com.CMD.ui.main.drawer;

import com.CMD.util.RequestAssistant;
import com.CMD.util.WindowStyle;
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
            aboutCmdButton, settingsButton, expensesButton;

    /*
     * Method to handle the 4 buttons on the menuBar.
     *This method calls the createStage() method to create the stage for each button pressed.
     */

    public void handleButtonPressed(ActionEvent event) throws Exception{
        if (event.getSource().equals(addNewMemberButton)){
            loadWindow("/com/CMD/ui/addmember/add_member.fxml");

        }else if (event.getSource().equals(viewMemberRecordButton)){
            loadWindow("/com/CMD/ui/viewrecord/view_records.fxml");

        }else if (event.getSource().equals(addPaymentRecordButton)){
            loadWindow("/com/CMD/ui/addpayment/add_payment.fxml");

        }else if (event.getSource().equals(aboutCmdButton)){
            loadWindow("/com/CMD/ui/about/about_CMD.fxml", "About");

        }else if (event.getSource().equals(settingsButton)) {
            loadWindow("/com/CMD/ui/settings/settings.fxml", "Settings");
        }else if (event.getSource().equals(expensesButton)){
            loadWindow("/com/CMD/ui/addexpenses/add_expenses.fxml");
        }
    }


    //    Method to create the modal Stage when a button is pressed
    public void loadWindow(String rootFile) throws Exception {
        Stage stage;
        Parent root;

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        root = FXMLLoader.load(DrawerController.class.getResource(rootFile));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        WindowStyle.allowDrag(root, stage);
        stage.show();
    }

    //    Method to create the modal Stage when a button is pressed
    public void loadWindow(String rootFile, String title) throws Exception {
        Stage stage;
        Parent root;

        stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        RequestAssistant.setStageIcon(stage);
        root = FXMLLoader.load(getClass().getResource(rootFile));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        WindowStyle.allowDrag(root, stage);
        stage.show();
    }
}
