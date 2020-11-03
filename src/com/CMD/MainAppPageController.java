package com.CMD;

import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainAppPageController implements Initializable {

    @FXML
    public ScrollPane scroll_pane;

    @FXML
    public AnchorPane displayPane;

    @FXML
    private AnchorPane blur_Pane, drawerPane;

    @FXML
    public AnchorPane mainAppPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    private ObservableList<Member> members;

    private final int firstVBoxWidth = 200;
    private final int firstVBoxHeight = 180;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//      Initialize the drawer with its contents
        try {
            initDrawer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Platform.runLater(() -> {
            members = DataBaseHandler.getInstance().getMembers();
            loadImages();
        });
    }

    private void loadImages(){
        displayPane.getChildren().remove(blur_Pane);
        for (int i = 0; i < members.size(); i++) {
            addNewMemberImage(members.get(i), i);
        }

        blur_Pane.setPrefHeight(180 * ((members.size() / 4) + 1));

        displayPane.getChildren().add(blur_Pane);

    }

    private void addNewMemberImage(Member member, int i) {
        if (member != null) {
            int firstVBoxLayoutY = 39;
            int firstVBoxLayoutX = 20;
            if (i == 0) {
                Label label = createLabel(member.getFirstName().get() + " " + member.getLastName().get());

                Circle circle = createCircle(member.getImgUrl());

                VBox vBox = createVBox(firstVBoxLayoutX, firstVBoxLayoutY);
                vBox.getChildren().add(circle);
                vBox.getChildren().add(label);

                displayPane.getChildren().add(vBox);
            }
            else if (i >= 4) {
                Label label = createLabel(member.getFirstName().get() + " " + member.getLastName().get());

                Circle circle = createCircle(member.getImgUrl());

                VBox vBox;
                if (i % 4 == 0) {
                    vBox = createVBox(firstVBoxLayoutX, firstVBoxLayoutY + (firstVBoxHeight * (i / 4)));

                }
                else {
                    vBox = createVBox(firstVBoxLayoutX + (firstVBoxWidth * (i % 4)), firstVBoxLayoutY + (firstVBoxHeight * (i / 4)));

                }

                vBox.getChildren().add(circle);
                vBox.getChildren().add(label);

                displayPane.getChildren().add(vBox);
            } else  {
                Label label = createLabel(member.getFirstName().get() + " " + member.getLastName().get());

                Circle circle = createCircle(member.getImgUrl());

                VBox vBox = createVBox(firstVBoxLayoutX + (firstVBoxWidth * (i % 4)), firstVBoxLayoutY);
                vBox.getChildren().add(circle);
                vBox.getChildren().add(label);

                displayPane.getChildren().add(vBox);
            }
        }


    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Segoe Script", 14));
        return label;
    }

    private Circle createCircle(String imageUrl) {
        Circle circle = new Circle(60);
        String fileString = new File(imageUrl).toURI().toString();
        Image image = new Image(fileString);
        circle.setFill(new ImagePattern(image));
        return circle;
    }

    private VBox createVBox(int layoutX, int layoutY) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(layoutX);
        vBox.setLayoutY(layoutY);
        vBox.setPrefHeight(firstVBoxHeight);
        vBox.setPrefWidth(firstVBoxWidth);

        return vBox;
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

            if (drawer.isClosed()) {
                blur_Pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
                blur_Pane.setOpacity(0.67);
                drawerPane.toFront();
                drawer.open();
            } else {
                blur_Pane.setOpacity(0);
                drawer.close();
                drawerPane.toBack();
            }

            List<Member> newMembers = DataBaseHandler.getInstance().getNewMembers();
            if (newMembers != null) {
                displayPane.getChildren().remove(blur_Pane);
                int quantity = DataBaseHandler.getInstance().getMembers().size() - 1;
                for (Member m : newMembers) {
                    addNewMemberImage(m, quantity - newMembers.indexOf(m));
                }

                blur_Pane.setPrefHeight(180 * ((quantity / 4) + 1));

                displayPane.getChildren().add(blur_Pane);
            }
        });
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

}
