package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizController {
    @FXML
    private MenuButton gameMenuButton;

    @FXML
    protected void onRestartClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent introRoot = loader.load();

            Stage stage = (Stage) gameMenuButton.getScene().getWindow();
            stage.setScene(new Scene(introRoot, 780, 460));
            stage.setTitle("Java Quizz - Introduction");
        } catch (IOException exception) {
            AppMenuActions.showError("Impossible de recharger l'introduction.");
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
