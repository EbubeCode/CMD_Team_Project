package CMD;

import CMD.controller.RequestHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainAppPageController implements Initializable {

    @FXML
    private Circle rich_circle, chyy_circle, ebube_circle, mike_circle, frank_circle,
            ephraim_circle, pius_circle, oge_circle, kachi_circle, ogadi_circle;

    @FXML
    private Label close_label, minimizeLabel, teamMembersLabel;

    @FXML
    private Button menuButton, addNewMemberButton, viewMemberRecordButton, addPaymentRecordButton, aboutCmdButton;

    @FXML
    private AnchorPane menuBarPane, teamMembersPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\chinyere.png", chyy_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\Ebube.png", ebube_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\Michael.png", mike_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\Franklin.png", frank_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\Pius.png", pius_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\Ogechi.png", oge_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\kachi.png", kachi_circle);
        addImage("C:\\Users\\User\\IdeaProjects\\CMD_Team_Project\\src\\resources\\image_res\\Ogadi.png", ogadi_circle);

    }
    /*
     * Method for showing the pane with the four buttons.
     *That is the menuBarPane.
     */
    public void handleMenuButton() {
        menuBarPane.setVisible(!menuBarPane.isVisible());
    }

//    Method implementation abstracted using the RequestHandler Singleton class
    public void closeLabelPressed() {
        RequestHandler.getInstance().handleClose();
    }

    public void minimizeLabelPressed(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMinimize((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }


    public void addImage(String imageString, Circle circle){
        imageString = new File(imageString).toURI().toString();
        Image image = new Image(imageString);
        circle.setFill(new ImagePattern(image));

    }
}
