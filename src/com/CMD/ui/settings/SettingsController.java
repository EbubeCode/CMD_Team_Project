package com.CMD.ui.settings;

import com.CMD.alert.AlertMaker;
import com.CMD.data.callback.GenericCallback;
import com.CMD.data.model.MailServerInfo;
import com.CMD.database.DataBaseHandler;
import com.CMD.database.DataHelper;
import com.CMD.model.Record;
import com.CMD.ui.mail.MailController;
import com.CMD.util.RequestAssistant;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 *
 @author CMD
 *
 *
 */

public class SettingsController implements Initializable, GenericCallback {

    private static final Logger LOGGER = LogManager.getLogger(DataBaseHandler.class.getName());
    @FXML
    private StackPane rootContainer;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField serverName;

    @FXML
    private JFXTextField smtpPort;

    @FXML
    private JFXTextField emailAddress;

    @FXML
    private JFXPasswordField emailPassword;

    @FXML
    private JFXCheckBox sslCheckbox;

    @FXML
    private JFXSpinner progressSpinner;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }

    @FXML
    void handleSaveButtonAction() {
        String usernameText = username.getText();
        String passwordText = password.getText();

        Preferences preferences = Preferences.getPreferences();
        preferences.setUsername(usernameText);
        preferences.setPassword(passwordText);

        Preferences.writePreferencesToFile(preferences);
    }

    private Stage getStage() {
        return ((Stage) username.getScene().getWindow());
    }


    private void initDefaultValues(){
        Preferences preferences = Preferences.getPreferences();

        username.setText(String.valueOf(preferences.getUsername()));
        String passHash = String.valueOf(preferences.getPassword());

        password.setText(passHash.substring(0, Math.min(passHash.length(), 10)));


        loadMailServerConfigurations();
    }


    private void loadMailServerConfigurations() {
        MailServerInfo mailServerInfo = DataHelper.loadMailServerInfo();

        if (mailServerInfo != null){
            LOGGER.log(Level.INFO, "Mail server info loaded from DB");
            serverName.setText(mailServerInfo.getMailServer());
            smtpPort.setText(String.valueOf(mailServerInfo.getPort()));
            emailAddress.setText(mailServerInfo.getEmailID());
            emailPassword.setText(mailServerInfo.getPassword());
            sslCheckbox.setSelected(mailServerInfo.getSslEnabled());
        }
    }


    @FXML
    void handleMailAction() {
        MailServerInfo mailServerInfo = readMailServerInfo();
        if (mailServerInfo != null){
            MailController controller = (MailController) RequestAssistant.loadWindow(getClass().getResource("/com/CMD/ui/mail/mail.fxml"), "Send Email", null);
            controller.setMailServerInfo(mailServerInfo);
        }
    }

    private MailServerInfo readMailServerInfo() {
        try{
            MailServerInfo mailServerInfo
                    = new MailServerInfo(serverName.getText(), Integer.parseInt(smtpPort.getText()),
                    emailAddress.getText(), emailPassword.getText(), sslCheckbox.isSelected());
            if (!mailServerInfo.validate() || !RequestAssistant.validateEmailAddress(emailAddress.getText())) {
                throw new InvalidParameterException();
            }
            return mailServerInfo;
        }catch (Exception ex){
            AlertMaker.showErrorMessage("Invalid Entries Found", "Correct input and try again");
            LOGGER.log(Level.WARN, ex);
        }
        return null;
    }

    @FXML
    void saveMailServerConfiguration() {
        MailServerInfo mailServerInfo = readMailServerInfo();
        if (mailServerInfo != null){
            if (DataHelper.updateMailServerInfo(mailServerInfo)){
                AlertMaker.showSimpleAlert("Success", "Saved Successfully");
            }else{
                AlertMaker.showErrorMessage("Failed", "Something went wrong!");
            }
        }
    }

    @FXML
    void handleDatabaseExportAction() {
        List<Record> records = DataBaseHandler.getInstance().getAllMembersRecords();

        Stage stage = (Stage) emailAddress.getScene().getWindow();

        Platform.runLater(() -> {
            RequestAssistant.initPDFExport(rootContainer, anchorPane, stage, records, this);
        });
        progressSpinner.setVisible(true);
    }

    @Override
    public Object taskCompleted(Object val) {
        progressSpinner.setVisible(false);
        return null;
    }
}
