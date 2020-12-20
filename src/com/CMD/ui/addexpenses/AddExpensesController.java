package com.CMD.ui.addexpenses;

import com.CMD.alert.AlertMaker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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

    }
}
