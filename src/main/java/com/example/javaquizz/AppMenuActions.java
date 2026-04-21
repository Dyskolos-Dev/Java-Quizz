package com.example.javaquizz;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class AppMenuActions {
    private AppMenuActions() {
    }

    public static void showCredits() {
        try {
            URL resource = HelloApplication.class.getResource("/com/example/javaquizz/credits-view.fxml");
            if (resource == null) {
                throw new IOException("Ressource credits-view.fxml introuvable");
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Parent creditsRoot = loader.load();

            Stage creditsStage = new Stage();
            creditsStage.setTitle("Credits");
            creditsStage.initModality(Modality.APPLICATION_MODAL);
            creditsStage.setResizable(false);
            creditsStage.setScene(new Scene(creditsRoot, 480, 260));
            creditsStage.showAndWait();
        } catch (IOException exception) {
            showError("Impossible de charger la page des credits.");
        }
    }

    public static void showError(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        error.setTitle("Erreur");
        error.setHeaderText("Navigation");
        error.showAndWait();
    }

    public static void quit() {
        Platform.exit();
    }
}
