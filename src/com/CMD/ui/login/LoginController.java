package com.CMD.ui.login;

import com.CMD.ui.settings.Preferences;
import com.CMD.util.RequestAssistant;
import com.CMD.util.WindowStyle;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final static Logger LOGGER = LogManager.getLogger(LoginController.class.getName());

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    Preferences preference;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preference = Preferences.getPreferences();

        username.setOnAction(event -> password.requestFocus());
        password.setOnAction(event -> handleLoginButtonAction());
    }

    @FXML
    void handleCancelButtonAction() {
        System.exit(0);
    }

    @FXML
    void handleLoginButtonAction() {
        String uname = StringUtils.trimToEmpty(username.getText());
        String pword = DigestUtils.sha1Hex(password.getText());

        if (uname.equals(preference.getUsername()) && pword.equals(preference.getPassword())) {
            closeStage();
            loadMain();
            LOGGER.log(Level.INFO, "User successfully logged in {}", uname);
        }
        else {
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
        }
    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/CMD/ui/main/main.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
            WindowStyle.allowDrag(parent, stage);
            RequestAssistant.setStageIcon(stage);
        }
        catch (IOException ex) {
            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }
}
