package com.CMD;

import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;

public class ViewMemberRecordController {

    @FXML
    private AnchorPane select_member_pane, view_member_pane;

    @FXML
    private JFXButton view_record_button, ok_button;

    @FXML
    private Label close_label, select_member_label, member_name_label, member_email_label, member_mobile_label;

    @FXML
    private Circle picture_circle;

    @FXML
    private TableView<Member> name_table;

    @FXML
    private TableView<?> record_table;

    @FXML
    private TableColumn<?, ?> name_table_column;

    @FXML
    private TableColumn<?, ?> date_table_column;

    @FXML
    private TableColumn<?, ?> amount_table_column;

    public void initialize() {
        ObservableList<Member> members = DataBaseHandler.getInstance().getMembers();

        TableColumn<Member, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(param -> param.getValue().getFirstName());

        TableColumn<Member, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(param -> param.getValue().getLastName());
        name_table.setItems(members);

        name_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name_table.getColumns().addAll(firstNameCol, lastNameCol);
    }

    @FXML
    public void closeLabelPressed(){
        RequestHandler.getInstance().handleCloseLabel(close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

    @FXML
    public void handleViewRecord() {
        Member member = name_table.getSelectionModel().getSelectedItem();
        if (member != null) {
            select_member_pane.setVisible(false);


            String fileString = new File(member.getImgUrl()).toURI().toString();
            Image image = new Image(fileString);
            picture_circle.setFill(new ImagePattern(image));


        }
    }

    @FXML
    public void handleOkButton() {
        select_member_pane.setVisible(true);
    }
}
