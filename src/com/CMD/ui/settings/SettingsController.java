package com.CMD.ui.settings;

import com.CMD.alert.AlertMaker;
import com.CMD.data.model.MailServerInfo;
import com.CMD.database.DataBaseHandler;
import com.CMD.database.DataHelper;
import com.CMD.ui.mail.TestMailController;
import com.CMD.ui.main.MainAppPageController;
import com.CMD.util.RequestAssistant;
import com.CMD.util.WindowStyle;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.jfr.internal.JVM;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
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
    void handleTestMailAction(ActionEvent event) throws Exception {
        MailServerInfo mailServerInfo = readMailServerInfo();
        if (mailServerInfo != null){
            TestMailController controller = (TestMailController) RequestAssistant.loadWindow(getClass().getResource("/com/CMD/ui/mail/test_mail.fxml"), "Test Email", null);
            controller.setMailServerInfo(mailServerInfo);
        }
    }

    private MailServerInfo readMailServerInfo() {
        try{
            MailServerInfo mailServerInfo
                    = new MailServerInfo(serverName.getText(), Integer.parseInt(smtpPort.getText()), emailAddress.getText(), emailPassword.getText(), sslCheckbox.isSelected());
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
    void saveMailServerConfiguration(ActionEvent event) {
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
    void handleDatabaseExportAction(ActionEvent event) {

    }

}
