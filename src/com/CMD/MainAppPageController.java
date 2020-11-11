package com.CMD;

import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @FXML
    private Label teamMembers;

    private ObservableList<Member> members;

    private Map<VBox, Member> memberMap;


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

        memberMap = new HashMap<>();

        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Platform.runLater(() -> {
            members = DataBaseHandler.getInstance().getMembers();
            loadImages();
        });
    }


//    Method to Load the images in the displayPane
    private void loadImages(){
        displayPane.getChildren().remove(blur_Pane);
        for (int i = 0; i < members.size(); i++) {
            addNewMemberImage(members.get(i), i);
        }

        blur_Pane.setPrefHeight(180 * ((members.size() / 4) + 1));


    }


//    Method to add a profile image for a registered member.
    private void addNewMemberImage(Member member, int i) {
        if (member != null) {
            int firstVBoxLayoutY = 39;
            int firstVBoxLayoutX = 20;
            VBox vBox;
            if (i == 0) {
                Label label = createLabel(member.getFirstName().get() + " " + member.getLastName().get());

                Circle circle = createCircle(member.getImgUrl());

                vBox = createVBox(firstVBoxLayoutX, firstVBoxLayoutY);
                vBox.getChildren().add(circle);
                vBox.getChildren().add(label);

                displayPane.getChildren().add(vBox);
            }
            else if (i >= 4) {
                Label label = createLabel(member.getFirstName().get() + " " + member.getLastName().get());

                Circle circle = createCircle(member.getImgUrl());

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

                vBox = createVBox(firstVBoxLayoutX + (firstVBoxWidth * (i % 4)), firstVBoxLayoutY);
                vBox.getChildren().add(circle);
                vBox.getChildren().add(label);

                displayPane.getChildren().add(vBox);
            }
            memberMap.put(vBox, member);

        }
    }


//    Method to create profile Label
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Segoe Script", 14));

        return label;
    }

//    Method to create profile Circle
    private Circle createCircle(String imageUrl) {
        Circle circle = new Circle(60);
        if(imageUrl != null) {
            String fileString = new File(imageUrl).toURI().toString();
            Image image = new Image(fileString);
            circle.setFill(new ImagePattern(image));
        }
        return circle;
    }

//    Method to create profile VBox
    private VBox createVBox(int layoutX, int layoutY) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(layoutX);
        vBox.setLayoutY(layoutY);
        vBox.setPrefHeight(firstVBoxHeight);
        vBox.setPrefWidth(firstVBoxWidth);
        vBox.setCursor(Cursor.HAND);

        ContextMenu ctx = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Update Profile");
        MenuItem menuItem2 = new MenuItem("Delete Profile");

        menuItem1.setOnAction(event -> {
            DataBaseHandler.getInstance().setUpdateMember(memberMap.get(vBox));
            try {
                DrawerController.createStage("ui/updateMemberProfile.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            displayPane.getChildren().add(blur_Pane);
            blur_Pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
            blur_Pane.setOpacity(0.67);
           });

        menuItem2.setOnAction(event -> {
            vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            displayPane.getChildren().add(blur_Pane);
            blur_Pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
            blur_Pane.setOpacity(0.67);
            deleteMember(vBox);
        });

        ctx.getItems().addAll(menuItem1, menuItem2);

        vBox.getStylesheets().add(getClass().getResource("util/style.css").toExternalForm());

        ctx.setAutoHide(true);

        vBox.setOnMousePressed(event -> {
            if(event.isSecondaryButtonDown()){
                ctx.show(vBox, event.getScreenX(), event.getScreenY());
           } else {
                ctx.hide();
            }
        });
        vBox.setOnMouseEntered(p -> vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY))));
        vBox.setOnMouseExited(p -> vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))));

        return vBox;
    }

    //This method is called incase a member needs to be deleted

    private void deleteMember(VBox vBox) {
        Member member = memberMap.get(vBox);
        ButtonType buttonType = RequestHandler.getInstance().showAlertOption("You are about to remove " + member.getFirstName().get()
                        + " from the team", "Remove member", Alert.AlertType.WARNING);
        if (buttonType == ButtonType.OK) {
            DataBaseHandler.getInstance().deleteMember(member);
            members.remove(member);
            displayPane.getChildren().clear();
            displayPane.getChildren().add(teamMembers);
            loadImages();
        }
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
                displayPane.getChildren().add(blur_Pane);
                blur_Pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
                blur_Pane.setOpacity(0.67);
                drawerPane.toFront();
                drawer.open();
            } else {
                blur_Pane.setOpacity(0);
                drawer.close();
                drawerPane.toBack();
                displayPane.getChildren().remove(blur_Pane);
            }


            updateMainAppImages();
        });
        blur_Pane.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
            if(!hamburger.isVisible()) {
                hamburger.setVisible(true);
            }else if(task.getRate() == 1){
                task.setRate(task.getRate() * -1);
                task.play();
            }

            blur_Pane.setOpacity(0);
            drawer.close();
            drawerPane.toBack();
            displayPane.getChildren().remove(blur_Pane);

            updateMainAppImages();
        });
    }
// Method to update Main app images, in the case where a user was added or updated profile
    private void updateMainAppImages() {
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

        Member member = DataBaseHandler.getInstance().getUpdateMember();
        if(member != null) {
            for (VBox vBox: memberMap.keySet()) {
                if (member.equals(memberMap.get(vBox))) {
                    Circle circle = (Circle) vBox.getChildren().get(0);
                    String fileString = new File(member.getImgUrl()).toURI().toString();
                    Image image = new Image(fileString);
                    circle.setFill(new ImagePattern(image));

                    Label label = (Label) vBox.getChildren().get(1);
                    label.setText(member.getFirstName().get() + " " + member.getLastName().get());
                }
            }
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

}
