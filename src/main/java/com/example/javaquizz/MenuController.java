package com.example.javaquizz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button restartButton;

    @FXML
    protected void onRestartClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(root, 780, 460));
            stage.setTitle("Java Quizz - Introduction");
        } catch (IOException exception) {
            showError("Impossible de recommencer l'introduction.");
        }
    }

    @FXML
    protected void onQuitClick() {
        Platform.exit();
    }

    @FXML
    protected void onCreditsClick() {
        AppMenuActions.showCredits();
    }

    private void showError(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        error.setTitle("Erreur");
        error.setHeaderText("Navigation");
        error.showAndWait();
    }
}
