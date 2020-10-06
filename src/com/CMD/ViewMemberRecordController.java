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
    private AnchorPane view_member_pane;

    @FXML
    private JFXListView<?> record_listview;

    @FXML
    private Circle picture_circle;

    @FXML
    private JFXButton ok_button;

    @FXML
    private Label member_name_label, close_label;

    @FXML
    private Label member_email_label;

    @FXML
    private Label member_mobile_label;

    @FXML
    private AnchorPane select_member_pane;

    @FXML
    private JFXButton view_record_button;

    @FXML
    private JFXListView<?> select_member_listview;

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
