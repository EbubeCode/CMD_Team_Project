package com.CMD.ui.main;

import com.CMD.model.Member;
import com.CMD.database.DataBaseHandler;
import com.CMD.alert.AlertMaker;
import com.CMD.util.WindowStyle;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class MainAppPageController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private ScrollPane scroll_pane;

    @FXML
    public AnchorPane displayPane;

    @FXML
    private AnchorPane drawerPane;

    @FXML
    public AnchorPane mainAppPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    private ObservableList<Member> members;

    private Map<VBox, Member> memberMap;

    BoxBlur blur = new BoxBlur(3, 3, 3);


    private final int firstVBoxWidth = 200;
    private final int firstVBoxHeight = 180;

    private boolean isDisplayPaneBlurred = false;

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

        displayPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
            if (isDisplayPaneBlurred) {
                displayPane.setEffect(null);
                isDisplayPaneBlurred = false;
                updateMainAppImages();
            }
        });
    }


//    Method to Load the images in the displayPane
    private void loadImages(){
        for (int i = 0; i < members.size(); i++) {
            addNewMemberImage(members.get(i), i);
        }
    }


//    Method to add a profile image for a registered member.
    private void addNewMemberImage(Member member, int i){
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
        MenuItem menuItem3 = new MenuItem("Refresh");

        menuItem1.setOnAction(event -> {
            displayPane.setEffect(blur);
            isDisplayPaneBlurred = true;
            DataBaseHandler.getInstance().setUpdateMember(memberMap.get(vBox));
            try {
                loadStage("/com/CMD/ui/updatemember/update_member.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        menuItem2.setOnAction(event -> {
            displayPane.setEffect(blur);
            vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            deleteMember(vBox);
            displayPane.setEffect(null);
        });


        menuItem3.setOnAction(event ->
                refresh()
        );

        ctx.getItems().addAll(menuItem1, menuItem2, menuItem3);

        vBox.getStylesheets().add(getClass().getResource("/com/CMD/util/style_util/dark-theme.css").toExternalForm());
        ctx.getStyleClass().add("colored-context-menu");

        ctx.setAutoHide(true);

        vBox.setOnMousePressed(event -> {
            if(event.isSecondaryButtonDown()){
                ctx.show(vBox, event.getScreenX(), event.getScreenY());
                vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
           } else {
                ctx.hide();
            }
        });

        vBox.setOnMouseEntered(p -> vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#34495e"), CornerRadii.EMPTY, Insets.EMPTY))));
        vBox.setOnMouseExited(p -> vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY))));

        return vBox;
    }


    public void refresh() {
        members.clear();
        updateMainAppImages();
    }


//    This method is called in case a member needs to be deleted
    private void deleteMember(VBox vBox) {
        Member member = memberMap.get(vBox);
        JFXButton okButton = new JFXButton("Okay");
        JFXButton cancelButton = new JFXButton("Cancel");
        AlertMaker.showMaterialDialog(rootPane, mainAppPane, Arrays.asList(okButton, cancelButton), "Remove Member", "You are about to remove " + member.getFirstName().get()
                + " from the team");

        okButton.setOnAction(event -> {
            DataBaseHandler.getInstance().deleteMember(member);
            members.remove(member);
            displayPane.getChildren().clear();
            loadImages();
        });

        cancelButton.setOnAction(event -> {
            JFXButton button = new JFXButton("Okay");
            AlertMaker.showMaterialDialog(rootPane, mainAppPane, Collections.singletonList(button), "Delete Cancelled", null);
        });

    }

    


//    Method to inflate the drawer_content with the Drawer and adding an event listener to the Hamburger.
    private void initDrawer() throws IOException {

        VBox menuBar = FXMLLoader.load(getClass().getResource("/com/CMD/ui/main/drawer/drawer_content.fxml"));
        drawer.setSidePane(menuBar);

        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {

            task.setRate(task.getRate() * -1);
            task.play();
            if (drawer.isOpened()){
                drawerPane.toBack();
                displayPane.setEffect(null);
                drawer.close();
                updateMainAppImages();
            }else{
                drawerPane.toFront();
                displayPane.setEffect(blur);
                drawer.open();
            }
        });
        drawerPane.toBack();
        updateMainAppImages();
    }


// Method to update Main app images, in the case where a user was added or updated profile
    private void updateMainAppImages() {
        List<Member> newMembers = DataBaseHandler.getInstance().getNewMembers();
        if (newMembers != null) {
            int quantity = DataBaseHandler.getInstance().getMembers().size() - 1;
            for (Member m : newMembers) {
                addNewMemberImage(m, quantity - newMembers.indexOf(m));
            }
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


//    Method to create a stage
    public void loadStage(String rootFile) throws Exception {
        Stage stage;
        Parent root;

        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        root = FXMLLoader.load(getClass().getResource(rootFile));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        WindowStyle.allowDrag(root, stage);
        stage.show();

    }


//    Method implementation abstracted using the RequestHandler Singleton class
    public void closeLabelPressed() {
        JFXButton yesButton = new JFXButton("Okay");
        JFXButton cancelButton = new JFXButton("Cancel");
        AlertMaker.showMaterialDialog(rootPane, mainAppPane, Arrays.asList(yesButton, cancelButton), "Exit", "Are you sure you want to exit?");
        yesButton.setOnAction(event -> Platform.exit());
    }

    public void minimizeLabelPressed(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMinimize((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

}
