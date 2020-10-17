package com.CMD;

import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class ViewMemberRecordController {

    @FXML
    private AnchorPane select_member_pane, view_member_pane;

    @FXML
    private JFXButton view_record_button, ok_button;

    @FXML
    private Label close_label, select_member_label, member_name_label, member_email_label, member_mobile_label;

    @FXML
    private Circle picture_circle;

    @FXML
    private TableView<?> name_table;

    @FXML
    private TableView<?> record_table;

    @FXML
    private TableColumn<?, ?> name_table_column;

    @FXML
    private TableColumn<?, ?> date_table_column;

    @FXML
    private TableColumn<?, ?> amount_table_column;

    @FXML
    public void closeLabelPressed(){
        RequestHandler.getInstance().handleCloseLabel(close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }
}
