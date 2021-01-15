package com.CMD.ui.addexpenses;

import animatefx.animation.ZoomIn;
import com.CMD.alert.AlertMaker;
import com.CMD.database.DataBaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class AddExpensesController {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane add_expenses_pane;

    @FXML
    private JFXTextField amount_text_field;

    @FXML
    private JFXTextArea description_text_area;

    @FXML
    private Label add_data_close_label;

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(AddExpensesController.class.getName());


    @FXML
    public void closeLabelPressed() {
        AlertMaker.getInstance().handleCloseLabel(add_data_close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

//    TODO: Add the Expenses to the database here
    public void handleAddExpenses() {
        String details = description_text_area.getText();
        int amount;

        try {

            amount = Integer.parseInt(amount_text_field.getText());
            try {

                Boolean success = DataBaseHandler.getInstance().insertRecord("-" + amount, LocalDate.now().getMonth().toString(), details,
                        LocalDate.now().getYear());
                JFXButton yesBtn = new JFXButton("Yes");
                JFXButton noBtn = new JFXButton("No");
                noBtn.setOnAction((ActionEvent event1) -> {
                    Stage stage = (Stage) amount_text_field.getScene().getWindow();
                    stage.close();
                });
                if (success) {
                    AlertMaker.showMaterialDialog(rootPane, add_expenses_pane, Arrays.asList(yesBtn, noBtn), "Completed",
                            "Record has been added successfully.\nWould you like to add another record?");
                    amount_text_field.setText("");
                    description_text_area.setText("");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
            }
        } catch (NumberFormatException e) {
            new ZoomIn(amount_text_field).play();
            amount_text_field.requestFocus();
        }


    }
}
