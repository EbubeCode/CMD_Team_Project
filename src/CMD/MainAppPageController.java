package CMD;

import CMD.controller.RequestHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class MainAppPageController implements Initializable {

    @FXML
    private Circle rich_circle, chyy_circle, ebube_circle, mike_circle, frank_circle,
            ephraim_circle, pius_circle, oge_circle, kachi_circle, ogadi_circle;

    @FXML
    private Button addNewMemberButton, viewMemberRecordButton, addPaymentRecordButton, aboutCmdButton;

    @FXML
    private AnchorPane menuBarPane, blur_Pane;

    private Map<String, Circle> imageMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageMap = new HashMap<>();
        addImageMapValues(imageMap);
        imageMap.forEach((s, c) -> addImage(s, c));

    }

    private void addImageMapValues(Map<String, Circle> map) {
        map.put("rich.png", rich_circle);
        map.put("chyy.png", chyy_circle);
        map.put("Ebube.png", ebube_circle);
        map.put("Michael.png", mike_circle);
        map.put("Franklin.png", frank_circle);
        map.put("ephraim.png", ephraim_circle);
        map.put("Pius.png", pius_circle);
        map.put("Ogechi.png", oge_circle);
        map.put("kachi.png", kachi_circle);
        map.put("Ogadi.png", ogadi_circle);

    }

    /*
     * Method for showing the pane with the four buttons.
     *That is the menuBarPane.
    */
    public void handleMenuButton() {
        menuBarPane.setVisible(!menuBarPane.isVisible());
        if (menuBarPane.isVisible()){
            blur_Pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
            blur_Pane.setOpacity(0.67);
        }else if (!menuBarPane.isVisible()){
            blur_Pane.setOpacity(0);
        }
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

    /*
     * Method for showing images on the mainAppPage.
     *This method first gets the image file path url and passes it into the circle.
     */

    private void addImage(String imageString, Circle circle){
        String dataPath = Paths.get("src\\resources\\image_res", imageString).toAbsolutePath().toString();
        String fileString = new File(dataPath).toURI().toString();
        Image image = new Image(fileString);
        circle.setFill(new ImagePattern(image));
    }
}
