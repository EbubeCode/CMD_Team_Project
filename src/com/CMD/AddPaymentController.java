package com.CMD;

import com.CMD.model.Member;
import com.CMD.util.DataBaseHandler;
import com.CMD.util.Months;
import com.CMD.util.RequestHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.SQLException;

public class AddPaymentController {

    @FXML
    private AnchorPane select_member_pane, add_data_pane;

    @FXML
    private JFXButton add_record_button;

    @FXML
    private Button proceedButton;

    @FXML
    private JFXTextField amount_text_field, month_text_field;

    @FXML
    private Label select_member_close_label, add_data_close_label, select_member_label, inv_data_label;

    @FXML
    private TableView<Member> name_table;

    private Member selectedMember;

    private static final String AMOUT_REGEX = "^(500a|500b|1000)$";


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
    public void closeLabelPressed() {
        RequestHandler.getInstance().handleCloseLabel(select_member_close_label);
        RequestHandler.getInstance().handleCloseLabel(add_data_close_label);
    }

    public void handleMouseEntered(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseEntered((Label) mouseEvent.getSource());
    }

    public void handleMouseExited(MouseEvent mouseEvent) {
        RequestHandler.getInstance().handleMouseExited((Label) mouseEvent.getSource());
    }

    @FXML
    public void handleProceedClick() {
        Member member = name_table.getSelectionModel().getSelectedItem();
        if (member != null) {
            select_member_pane.setVisible(false);
            selectedMember = member;
        }
    }

    @FXML
    public void handleAddRecord() {
        String monthText = month_text_field.getText().toUpperCase();
        String amount = amount_text_field.getText();
        Months[] months = Months.values();

        for (Months M: months) {
            if (M.toString().equals(monthText)) {
                if (amount.matches(AMOUT_REGEX)) {
                    try {
                        boolean success = DataBaseHandler.getInstance().insertRecord(amount, monthText, selectedMember.getID());
                        if (success) {
                            RequestHandler.getInstance().showAlert("Record Successfully Added",
                                    "Success!", Alert.AlertType.CONFIRMATION);
                            select_member_pane.setVisible(true);
                            amount_text_field.clear();
                            month_text_field.clear();
                        } else {
                            RequestHandler.getInstance().showAlert("Record already exists in records...",
                                    "Member Check", Alert.AlertType.INFORMATION);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
