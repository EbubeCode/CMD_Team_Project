package com.CMD.ui.addpayment;

import animatefx.animation.ZoomIn;
import com.CMD.alert.AlertMaker;
import com.CMD.database.DataBaseHandler;
import com.CMD.model.Member;
import com.CMD.util.Months;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;

public class AddPaymentController {
    @FXML
    private JFXCheckBox yearCheckBox;

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane select_member_pane, blurNode, add_data_pane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private JFXTextField amount_text_field, month_text_field, year_text_field;

    @FXML
    private Label select_member_close_label, add_data_close_label;

    @FXML
    private TableView<Member> name_table;

    private Member selectedMember;

    private static final String AMOUNT_REGEX = "^(500a|500b|1000)$";

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(AddPaymentController.class.getName());

    public void initialize() {
        Task<ObservableList<Member>> task = new DataBaseHandler.GetAllMembersTask();

        TableColumn<Member, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(param -> param.getValue().getFirstName());


        TableColumn<Member, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(param -> param.getValue().getLastName());

        name_table.itemsProperty().bind(task.valueProperty());
        name_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name_table.getColumns().addAll(firstNameCol, lastNameCol);

        name_table.setOnMouseClicked(event -> { // Enable double clicking of Members
            selectedMember = name_table.getSelectionModel().getSelectedItem();
            if(selectedMember != null && event.getClickCount() > 1) {
                select_member_pane.setVisible(false);
                year_text_field.setVisible(false);
                yearCheckBox.setSelected(false);
                year_text_field.setText("");
            }
        });

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
            year_text_field.setVisible(false);
            yearCheckBox.setSelected(false);
            year_text_field.setText("");
        }
    }

    @FXML
    public void handleAddRecord() {
        int year = 0;

        try {
            if(yearCheckBox.isSelected()) {
                year = Integer.parseInt(year_text_field.getText());

                if(year < 2020 || year > (LocalDate.now().getYear() + 1)) {
                   throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {

            year_text_field.requestFocus();
            year_text_field.setFocusColor(Color.valueOf("#d91e18"));
            new ZoomIn(year_text_field).play();
            return;
        }


        String monthText = month_text_field.getText().toUpperCase();

        String amount = amount_text_field.getText();
        Months[] months = Months.values();

        for (Months M: months) {
            if (month_text_field.getText().isEmpty() || amount_text_field.getText().isEmpty()) {
                month_text_field.getStyleClass().add("wrong-credentials");
                amount_text_field.getStyleClass().add("wrong-credentials");
            }
            if (amount_text_field.getText().isEmpty() || !amount.matches(AMOUNT_REGEX)){
                amount_text_field.setFocusColor(Color.valueOf("#d91e18"));
                amount_text_field.requestFocus();
                break;
            }
            else if (month_text_field.getText().isEmpty()){
                errorMonthTextField();
                break;
            } else {
                try {
                    if (M.toString().equals(monthText) || M.value.equals(monthText.substring(0, 3))) {
                        if (amount.matches(AMOUNT_REGEX)) {
                            try {

                                boolean success = DataBaseHandler.getInstance().insertRecord(amount, M.toString(), selectedMember.getID(),
                                        "Monthly Due", year);
                                if (success) {
                                    JFXButton okayButton = new JFXButton("Okay");
                                    AlertMaker.showMaterialDialog(rootPane, blurNode, Collections.singletonList(okayButton),
                                            "Success!", "Record Successfully Added");
                                    select_member_pane.setVisible(true);
                                    amount_text_field.clear();
                                    month_text_field.clear();
                                    amount_text_field.getStyleClass().add("text-field");
                                    month_text_field.getStyleClass().add("text-field");
                                } else {
                                    AlertMaker.showSimpleAlert("Member Check", "Record already exists in records...");
                                    month_text_field.requestFocus();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                break;

                            } catch (IndexOutOfBoundsException e) {
                                errorMonthTextField();
                                LOGGER.log(Level.ERROR, e.getMessage());
                                break;
                            }
                        }
                        if (M.equals(Months.DECEMBER)) {
                            errorMonthTextField();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
       }
    }

    private void errorMonthTextField(){
        month_text_field.requestFocus();
        month_text_field.getStyleClass().add("wrong-credentials");
        new ZoomIn(month_text_field).play();
    }

    @FXML
    public void handleYearCheckBox() {
        if(yearCheckBox.isSelected()) {
            year_text_field.setVisible(true);
        } else {
            year_text_field.setText("");
            year_text_field.setVisible(false);
        }
    }
}
