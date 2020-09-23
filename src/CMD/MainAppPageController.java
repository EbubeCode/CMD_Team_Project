package CMD;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    /* This method will be called when the close_label Label
     * has been clicked. It will call the WelcomePageContoller's handleCloseLabel method
     * for it to resuse the Alert dialog created by the method.
     */
    public void closeLabelPressed() {
        WelcomePageController close = new WelcomePageController();
        close.handleCloseLabel();
    }
}
