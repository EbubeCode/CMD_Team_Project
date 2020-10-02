package com.CMD;

import com.CMD.util.RequestHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private AnchorPane menuBarPane, blur_Pane, mainAppPane;

    private Map<String, Circle> imageMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageMap = new HashMap<>();
        addImageMapValues(imageMap);
        imageMap.forEach(this::addImage);   //method reference same as the forEach(Biconsumer -> override accept() method).
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



    /*
     * Method to handle the 4 buttons on the menuBar.
     *This method calls the createStage() method to create the stage for each button pressed.
    */

    public void handleButtonPressed(ActionEvent event) throws Exception{
       if (event.getSource() == addNewMemberButton){
          createStage("ui/addNewMember.fxml");

       }else if (event.getSource() == viewMemberRecordButton){

       }else if (event.getSource() == addPaymentRecordButton){

       }else if (event.getSource() == aboutCmdButton){

       }
    }


//    Method to create the modal Stage when a button is pressed
    private void createStage( String rootFile) throws Exception {
        Stage stage;
        Parent root;

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainAppPane.getScene().getWindow());
        root = FXMLLoader.load(getClass().getResource(rootFile));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
