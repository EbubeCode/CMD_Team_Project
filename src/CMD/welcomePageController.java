package CMD;

/* Copyright (c) 2020, CMD and/or its affiliates. All rights reserved.
 * CMD PROPRIETARY/CONFIDENTIAL. Use is subject to CMD license terms.
*/
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;

public class welcomePageController {
    @FXML
    private Label welcome_label;

    @FXML
    private Label motto_label;

    @FXML
    private Button proceed_button;

    @FXML
    private Label close_label;

    @FXML
    public void handleCloseLabel() {
        Alert alert = new Alert( Alert.AlertType.NONE,"Are you sure you want exit?",
                ButtonType.YES,  ButtonType.CANCEL);
        alert.setTitle("Exit");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.YES)) {
            Platform.exit();
        }

    }
}
