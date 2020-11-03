package com.CMD;

import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class MainAppPageController implements Initializable {

    @FXML
    public ScrollPane scroll_pane;

    @FXML
    private Circle rich_circle, chyy_circle, ebube_circle, mike_circle, frank_circle,
            ephraim_circle, pius_circle, oge_circle, kachi_circle, ogadi_circle;

    @FXML
    private AnchorPane blur_Pane, drawerPane;

    @FXML
    public AnchorPane mainAppPane;

    @FXML
    private JFXHamburger hamburger;


    private Map<String, Circle> imageMap;

    private ObservableList<Member> members;

    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//      Initialize the drawer with its contents
        try {
            initDrawer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        imageMap = new HashMap<>();
        addImageMapValues(imageMap);
        imageMap.forEach(this::addImage);   //method reference same as the forEach(Biconsumer -> override accept() method).


        members = DataBaseHandler.getInstance().getMembers();
    }


//    Method to inflate the drawer_content with the Drawer and adding an event listener to the Hamburger.
    private void initDrawer() throws IOException {
        VBox menuBar = FXMLLoader.load(getClass().getResource("ui/drawer_content.fxml"));
        drawer.setSidePane(menuBar);

        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
            task.setRate(task.getRate() * -1);
            task.play();

            if (drawer.isClosed()){
                 blur_Pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
                 blur_Pane.setOpacity(0.67);
                 drawerPane.toFront();
                 drawer.open();
            }else{
                blur_Pane.setOpacity(0);
                drawer.close();
                drawerPane.toBack();
            }
        });
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
