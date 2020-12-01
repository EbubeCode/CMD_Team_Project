package com.CMD.ui.mail;

import com.CMD.alert.AlertMaker;
import com.CMD.data.callback.GenericCallback;
import com.CMD.data.model.MailServerInfo;
import com.CMD.email.EmailUtil;
import com.CMD.util.RequestAssistant;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class TestMailController implements Initializable, GenericCallback {
    private final static Logger LOGGER = LogManager.getLogger(TestMailController.class.getName());

    @FXML
    private JFXTextField recipientAddressInput;

    @FXML
    private JFXProgressBar progressBar;

    private MailServerInfo mailServerInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void handleStartAction(ActionEvent event) {
        String toAddress = recipientAddressInput.getText();
        if (RequestAssistant.validateEmailAddress(toAddress)) {
            EmailUtil.sendTestMail(mailServerInfo, toAddress, this);
            progressBar.setVisible(true);
        } else {
            AlertMaker.showErrorMessage("Failed", "Invalid email address!");
        }
    }

    public void setMailServerInfo(MailServerInfo mailServerInfo) {
        this.mailServerInfo = mailServerInfo;
    }

    @Override
    public Object taskCompleted(Object val) {
        LOGGER.log(Level.INFO, "Callback received from Email Sender client {}", val);
        boolean result = (boolean) val;

        Platform.runLater(() -> {
            if (result) {
                AlertMaker.showSimpleAlert("Success", "Email successfully sent!");
            } else {
                AlertMaker.showErrorMessage("Failed", "Something went wrong!");
            }
            progressBar.setVisible(false);
        });
        return true;
    }
}
