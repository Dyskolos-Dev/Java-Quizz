package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameEndController {
    @FXML
    private Button restartButton;

    @FXML
    protected void onRestartClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent introRoot = loader.load();

            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(introRoot, 780, 460));
            stage.setTitle("Java Quizz - Introduction");
        } catch (IOException exception) {
            AppMenuActions.showError("Impossible de recommencer la partie.");
        }
    }

    @FXML
    protected void onCreditsClick() {
        AppMenuActions.showCredits();
    }

    @FXML
    protected void onQuitClick() {
        AppMenuActions.quit();
    }
}

