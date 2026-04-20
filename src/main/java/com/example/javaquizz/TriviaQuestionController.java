package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TriviaQuestionController {
    @FXML
    private MenuButton gameMenuButton;

    @FXML
    private VBox questionBox;

    @FXML
    private Label questionLabel;

    @FXML
    private VBox answersBox;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button nextButton;

    private TriviaAPI.Question currentQuestion;
    private boolean answered;

    @FXML
    public void initialize() {
        questionBox.setVisible(true);
        questionBox.setManaged(true);
        feedbackLabel.setText("Chargement...");
        nextButton.setVisible(false);
        nextButton.setManaged(false);
        loadQuestion();
    }

    private void loadQuestion() {
        new Thread(() -> {
            try {
                currentQuestion = TriviaAPI.fetchQuestion();
                javafx.application.Platform.runLater(this::displayQuestion);
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    feedbackLabel.setText("Impossible de charger la question");
                    answersBox.getChildren().clear();
                });
            }
        }).start();
    }

    private void displayQuestion() {
        if (currentQuestion == null) {
            feedbackLabel.setText("Impossible de charger la question");
            return;
        }

        questionLabel.setText(decodeHtml(currentQuestion.questionText));
        answersBox.getChildren().clear();

        for (String answer : currentQuestion.answers) {
            Button answerButton = new Button(decodeHtml(answer));
            answerButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #1e293b; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");
            answerButton.setWrapText(true);
            answerButton.setPrefHeight(60.0);
            answerButton.setMaxWidth(Double.MAX_VALUE);

            answerButton.setOnAction(event -> onAnswerClick(answer, answerButton));
            answersBox.getChildren().add(answerButton);
        }

        feedbackLabel.setText("");
        nextButton.setVisible(false);
        nextButton.setManaged(false);

        questionBox.setVisible(true);
        questionBox.setManaged(true);
        answered = false;
    }

    private void onAnswerClick(String selectedAnswer, Button clickedButton) {
        if (answered || currentQuestion == null) {
            return;
        }

        answered = true;
        String decodedCorrect = decodeHtml(currentQuestion.correctAnswer);
        String decodedSelected = decodeHtml(selectedAnswer);
        boolean isCorrect = decodedSelected.equals(decodedCorrect);

        if (isCorrect) {
            feedbackLabel.setText("Correct");
            feedbackLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #4ade80;");
            clickedButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #16a34a; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");
        } else {
            feedbackLabel.setText("Incorrect");
            feedbackLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ef4444;");
            clickedButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #dc2626; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");

            for (int i = 0; i < answersBox.getChildren().size(); i++) {
                Button btn = (Button) answersBox.getChildren().get(i);
                if (btn.getText().equals(decodedCorrect)) {
                    btn.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #16a34a; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");
                }
            }
        }

        nextButton.setVisible(true);
        nextButton.setManaged(true);
    }

    private String decodeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&#039;", "'")
                .replace("&apos;", "'");
    }

    @FXML
    protected void onNextClick() {
        goToQuizScreen();
    }

    @FXML
    protected void onRestartClick() {
        try {
            URL resource = HelloApplication.class.getResource("/com/example/javaquizz/hello-view.fxml");
            if (resource == null) {
                throw new IOException("Ressource hello-view.fxml introuvable");
            }
            FXMLLoader loader = new FXMLLoader(resource);
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

    private void goToQuizScreen() {
        try {
            URL resource = HelloApplication.class.getResource("/com/example/javaquizz/quiz-view.fxml");
            if (resource == null) {
                throw new IOException("Ressource quiz-view.fxml introuvable");
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Parent quizRoot = loader.load();

            Stage stage = (Stage) gameMenuButton.getScene().getWindow();
            stage.setScene(new Scene(quizRoot, 780, 460));
            stage.setTitle("Java Quizz - Quiz");
        } catch (IOException exception) {
            AppMenuActions.showError("Impossible de charger le quiz.");
        }
    }
}

