package com.example.javaquizz;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class CreditsController {
    @FXML
    private Hyperlink dyskolosLink;

    @FXML
    private Hyperlink prettyFlackoLink;

    @FXML
    protected void onOpenDyskolos() {
        openUrl(dyskolosLink.getText());
    }

    @FXML
    protected void onOpenPrettyFlacko() {
        openUrl(prettyFlackoLink.getText());
    }

    @FXML
    protected void onClose() {
        Stage stage = (Stage) dyskolosLink.getScene().getWindow();
        stage.close();
    }

    private void openUrl(String url) {
        HostServices hostServices = HelloApplication.getAppHostServices();
        if (hostServices == null) {
            AppMenuActions.showError("Ouverture navigateur indisponible.");
            return;
        }

        try {
            hostServices.showDocument(url);
        } catch (Exception exception) {
            AppMenuActions.showError("Impossible d'ouvrir le lien: " + url);
        }
    }
}
