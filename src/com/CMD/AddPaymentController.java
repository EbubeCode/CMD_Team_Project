package com.CMD;

import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AddPaymentController {

    @FXML
    private AnchorPane select_member_pane, add_data_pane;

    @FXML
    private JFXButton add_record_button;

    @FXML
    private Button proceedButton;

    @FXML
    private JFXTextField amount_text_field, month_text_field;

    @FXML
    private Label select_member_close_label, add_data_close_label, select_member_label, inv_data_label;

    @FXML
    private TableView<?> name_table;


    @FXML
    public void closeLabelPressed() {
        RequestHandler.getInstance().handleCloseLabel(select_member_close_label);
        RequestHandler.getInstance().handleCloseLabel(add_data_close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

}
