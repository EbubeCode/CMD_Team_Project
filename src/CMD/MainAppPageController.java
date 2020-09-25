package CMD;

import CMD.controller.RequestHandler;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainAppPageController {

    @FXML
    private AnchorPane mainAppPane;

    @FXML
    private Label close_label;

    @FXML
    private Label minimizeLabel;

    @FXML
    private Button menuButton;

    @FXML
    private AnchorPane menuBarPane;

    @FXML
    private Button addNewMemberButton;

    @FXML
    private Button viewMemberRecordButton;

    @FXML
    private Button addPaymentRecordButton;

    @FXML
    private Button aboutCmdButton;

    @FXML
    private AnchorPane teamMembersPane;

    @FXML
    private Label teamMembersLabel;

//    Method implementation abstracted using the RequestHandler Singleton class
    public void closeLabelPressed() {
        RequestHandler.getInstance().handleClose();
    }

    public void minimizeLabelPressed(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMinimize((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }
}
