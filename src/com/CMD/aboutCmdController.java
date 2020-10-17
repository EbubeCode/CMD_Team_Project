package com.CMD;

import com.CMD.util.RequestHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class aboutCmdController implements Initializable {

    @FXML
    private Label about_cmd_close_label;

    @FXML
    private Label show_profile_pane;

    @FXML
    public void closeLabelPressed(){
        RequestHandler.getInstance().handleCloseLabel(about_cmd_close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
