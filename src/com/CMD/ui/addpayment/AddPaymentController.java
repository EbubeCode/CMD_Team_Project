package com.CMD.ui.addpayment;

import animatefx.animation.Flash;
import com.CMD.model.Member;
import com.CMD.database.DataBaseHandler;
import com.CMD.util.Months;
import com.CMD.alert.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class AddPaymentController {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane select_member_pane, add_data_pane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private JFXTextField amount_text_field, month_text_field;

    @FXML
    private Label select_member_close_label, add_data_close_label, inv_data_label;

    @FXML
    private TableView<Member> name_table;

    private Member selectedMember;

    private static final String AMOUNT_REGEX = "^(500a|500b|1000)$";


    public void initialize() {
        Task<ObservableList<Member>> task = new DataBaseHandler.GetAllMembersTask();

        TableColumn<Member, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(param -> param.getValue().getFirstName());

        TableColumn<Member, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(param -> param.getValue().getLastName());

        name_table.itemsProperty().bind(task.valueProperty());
        name_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name_table.getColumns().addAll(firstNameCol, lastNameCol);

        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setVisible(true);

        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));

        new Thread(task).start();
    }

    @FXML
    public void closeLabelPressed() {
        AlertMaker.getInstance().handleCloseLabel(select_member_close_label);
        AlertMaker.getInstance().handleCloseLabel(add_data_close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        AlertMaker.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

    @FXML
    public void handleProceedClick() {
        Member member = name_table.getSelectionModel().getSelectedItem();
        if (member != null) {
            select_member_pane.setVisible(false);
            selectedMember = member;
            inv_data_label.setText(null);
        }
    }

    @FXML
    public void handleAddRecord() {
        String monthText = month_text_field.getText().toUpperCase();
        String amount = amount_text_field.getText();
        Months[] months = Months.values();

        for (Months M: months) {
            try {
                if (M.toString().equals(monthText) || M.value.equals(monthText.substring(0, 3))) {
                    if (amount.matches(AMOUNT_REGEX)) {
                        try {
                            boolean success = DataBaseHandler.getInstance().insertRecord(amount, M.toString(), selectedMember.getID());
                            if (success) {
                                AlertMaker.getInstance().showAlert("Record Successfully Added",
                                        "Success!", Alert.AlertType.CONFIRMATION);
                                select_member_pane.setVisible(true);
                                amount_text_field.clear();
                                month_text_field.clear();
                            } else {
                                AlertMaker.getInstance().showAlert("Record already exists in records...",
                                        "Member Check", Alert.AlertType.INFORMATION);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
                        inv_data_label.setTextFill(Color.valueOf("#009688"));
                        inv_data_label.setText("Invalid amount entry");
                        new Flash(inv_data_label).play();
                        amount_text_field.requestFocus();
                    }
                    break;
                }else{
                    inv_data_label.setTextFill(Color.valueOf("#009688"));
                    inv_data_label.setText("Invalid month entry");
                    new Flash(inv_data_label).play();
                    month_text_field.requestFocus();
                }
            } catch(IndexOutOfBoundsException e) {
                inv_data_label.setTextFill(Color.valueOf("#009688"));
                inv_data_label.setText("Invalid month entry");
                new Flash(inv_data_label).play();
                month_text_field.requestFocus();
            }

        }
    }

}