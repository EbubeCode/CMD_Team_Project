package com.CMD;

import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class ViewMemberRecordController {

    @FXML
    private AnchorPane view_member_pane, select_member_pane;

    @FXML
    private JFXListView<?> select_member_listView, record_listView;

    @FXML
    private Circle picture_circle;

    @FXML
    private JFXButton ok_button;

    @FXML
    private Label member_name_label, close_label, member_email_label, member_mobile_label;

    @FXML
    private JFXButton view_record_button;

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
