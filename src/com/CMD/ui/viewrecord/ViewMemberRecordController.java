package com.CMD.ui.viewrecord;

import com.CMD.alert.AlertMaker;
import com.CMD.database.DataBaseHandler;
import com.CMD.model.Member;
import com.CMD.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.sql.SQLException;

import static com.CMD.util.Months.*;

public class ViewMemberRecordController {

    @FXML
    private AnchorPane select_member_pane;

    @FXML
    private Label close_label, member_name_label, member_email_label,
            member_mobile_label, member_dob_label;

    @FXML
    private Circle picture_circle;

    @FXML
    private TableView<Member> name_table;

    @FXML
    private TableView<Record> record_table;

    @FXML
    private TableColumn<Record, String> date_table_column;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableColumn<Record, String> amount_table_column;

    ObservableList<Member> list = FXCollections.observableArrayList();

    public void initialize() {
        Task<ObservableList<Member>> task = new DataBaseHandler.GetAllMembersTask();

        TableColumn<Member, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(param -> param.getValue().getFirstName());

        TableColumn<Member, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(param -> param.getValue().getLastName());

        name_table.itemsProperty().bind(task.valueProperty());
        name_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name_table.getColumns().addAll(firstNameCol, lastNameCol);
        name_table.setOnMouseClicked(event -> {
            Member selectedMember = name_table.getSelectionModel().getSelectedItem();
            if(selectedMember != null && event.getClickCount() > 1) {
                viewMemberRecord(selectedMember);
            }
        });

        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setVisible(true);
        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));

        new Thread(task).start();

    }

    @FXML
    public void closeLabelPressed(){
        AlertMaker.getInstance().handleCloseLabel(close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

    @FXML
    public void handleViewRecord() {
        Member member = name_table.getSelectionModel().getSelectedItem();
        viewMemberRecord(member);

    }

    private void viewMemberRecord(Member member) {
        if (member != null) {
            select_member_pane.setVisible(false);

            String imgUrl = member.getImgUrl();
            if(imgUrl != null) {
                String fileString = new File(member.getImgUrl()).toURI().toString();
                Image image = new Image(fileString);
                picture_circle.setFill(new ImagePattern(image));
            }


            member_name_label.setText(member.getFirstName().get() + " " + member.getLastName().get());
            member_email_label.setText(member.getEmail());
            member_mobile_label.setText(member.getPhoneNumber());

            String dob = member.getDateOfBirth();
            if (!dob.isEmpty()) {
                String[] months = dob.split("/");
                member_dob_label.setText(months[0] + " " + getMonth(months[1]) + " " + months[2]);
            }

            try {

                ObservableList<Record> records = DataBaseHandler.getInstance().getRecords(member.getID());

                amount_table_column.setCellValueFactory(param -> param.getValue().amountProperty());
                date_table_column.setCellValueFactory(param -> param.getValue().monthProperty());

                record_table.setItems(records);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            record_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }else
            System.out.println("Member is null");
    }

    private String getMonth(String s) {

        switch (s) {
            case "01":
                return JANUARY.value;
            case "02":
                return FEBRUARY.value;
            case "03":
                return MARCH.value;
            case "04":
                return APRIL.value;
            case "05":
                return MAY.value;
            case "06":
                return JUNE.value;
            case "07":
                return JULY.value;
            case "08":
                return AUGUST.value;
            case "09":
                return SEPTEMBER.value;
            case "10":
                return OCTOBER.value;
            case "11":
                return NOVEMBER.value;
            case "12":
                return DECEMBER.value;
            default:
                return s;

        }
    }

    @FXML
    public void handleOkButton() {
        select_member_pane.setVisible(true);
    }
}
