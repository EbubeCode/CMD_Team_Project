package com.CMD;

import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class MainAppPageController2 implements Initializable {

    @FXML
    public ScrollPane scroll_pane;

    @FXML
    private Circle firstCircle;

    @FXML
    private VBox firstVBox;

    @FXML
    private Label firstNameLabel;


    @FXML
    private Button addNewMemberButton, viewMemberRecordButton, addPaymentRecordButton, aboutCmdButton;

    @FXML
    private AnchorPane menuBarPane, blur_Pane, mainAppPane, displayPane;


    private ObservableList<Member> members;

    private int firstVBoxLayoutX = 20, firstVBoxLayoutY = 39, firstVBoxWidth = 200, firstVBoxHeight = 180;








    @Override
    public void initialize(URL location, ResourceBundle resources) {


        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        members = DataBaseHandler.getInstance().getMembers();

        Platform.runLater(this::loadImages);
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
        addNewMemberImage(DataBaseHandler.getInstance().getNewMember(), DataBaseHandler.getInstance().getMembers().size() - 1);
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

    private void loadImages(){
            for (int i = 0; i < members.size(); i++) {
                if (i == 0) {
                    firstNameLabel.setText(members.get(i).getFirstName().get() + " " + members.get(i).getLastName().get());
                    String fileString = new File(members.get(i).getImgUrl()).toURI().toString();
                    Image image = new Image(fileString);
                    firstCircle.setFill(new ImagePattern(image));
                }
                else if (i >= 4) {
                    Label label = new Label(members.get(i).getFirstName().get() + " " + members.get(i).getLastName().get());
                    label.setTextFill(Color.WHITE);
                    label.setFont(new Font("Segoe Script", 12));


                    Circle circle = new Circle(60);
                    String fileString = new File(members.get(i).getImgUrl()).toURI().toString();
                    Image image = new Image(fileString);
                    circle.setFill(new ImagePattern(image));

                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);

                    if (i % 4 == 0) {
                        vBox.setLayoutY(firstVBoxLayoutY + (firstVBoxHeight * (i / 4)));
                        vBox.setLayoutX(firstVBoxLayoutX);
                    }
                    else {
                        vBox.setLayoutX(firstVBoxLayoutX + (firstVBoxWidth * (i % 4)));
                        vBox.setLayoutY(firstVBoxLayoutY + (firstVBoxHeight * (i / 4)));
                    }

                    vBox.setPrefHeight(firstVBoxHeight);
                    vBox.setPrefWidth(firstVBoxWidth);

                    vBox.getChildren().add(circle);
                    vBox.getChildren().add(label);

                    displayPane.getChildren().add(vBox);
                } else  {
                    Label label = new Label(members.get(i).getFirstName().get() + " " + members.get(i).getLastName().get());
                    label.setTextFill(Color.WHITE);
                    label.setFont(new Font("Segoe Script", 12));

                    Circle circle = new Circle(60);
                    String fileString = new File(members.get(i).getImgUrl()).toURI().toString();
                    Image image = new Image(fileString);
                    circle.setFill(new ImagePattern(image));

                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setLayoutX(firstVBoxLayoutX + (firstVBoxWidth * (i % 4)));
                    vBox.setLayoutY(firstVBoxLayoutY);
                    vBox.setPrefHeight(firstVBoxHeight);
                    vBox.setPrefWidth(firstVBoxWidth);

                    vBox.getChildren().add(circle);
                    vBox.getChildren().add(label);

                    displayPane.getChildren().add(vBox);
                }
            }

    }

    private void addNewMemberImage(Member member, int i) {
        if (member != null) {
            Label label = new Label(member.getFirstName().get() + " " + member.getLastName().get());
            label.setTextFill(Color.WHITE);
            label.setFont(new Font("Segoe Script", 12));


            Circle circle = new Circle(60);
            String fileString = new File(member.getImgUrl()).toURI().toString();
            Image image = new Image(fileString);
            circle.setFill(new ImagePattern(image));

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);

            if (i % 4 == 0) {
                vBox.setLayoutY(firstVBoxLayoutY + (firstVBoxHeight * (i / 4)));
                vBox.setLayoutX(firstVBoxLayoutX);
            }
            else {
                vBox.setLayoutX(firstVBoxLayoutX + (firstVBoxWidth * (i % 4)));
                vBox.setLayoutY(firstVBoxLayoutY + (firstVBoxHeight * (i / 4)));
            }

            vBox.setPrefHeight(firstVBoxHeight);
            vBox.setPrefWidth(firstVBoxWidth);

            vBox.getChildren().add(circle);
            vBox.getChildren().add(label);

            displayPane.getChildren().add(vBox);
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
        WindowStyle.allowDrag(root, stage);
        stage.show();
    }

    /*
     * Method to handle the 4 buttons on the menuBar.
     *This method calls the createStage() method to create the stage for each button pressed.
    */

    public void handleButtonPressed(ActionEvent event) throws Exception{
       if (event.getSource() == addNewMemberButton){
          createStage("ui/addNewMember.fxml");

       }else if (event.getSource() == viewMemberRecordButton){
           createStage("ui/viewMemberRecord.fxml");

       }else if (event.getSource() == addPaymentRecordButton){
           createStage("ui/addPayment.fxml");

       }else if (event.getSource() == aboutCmdButton){
           createStage("ui/aboutCMD.fxml");

       }
    }

}
