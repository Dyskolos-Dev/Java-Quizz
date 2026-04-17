package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TriviaQuestionController {
    @FXML
    private MenuButton gameMenuButton;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private VBox initialDialogueBox;

    @FXML
    private Button startQuizButton;

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

    @FXML
    private Label loadingLabel;

    private TriviaAPI.Question currentQuestion;
    private boolean answered = false;

    @FXML
    public void initialize() {
        initialDialogueBox.setVisible(true);
        initialDialogueBox.setManaged(true);
        questionBox.setVisible(false);
        questionBox.setManaged(false);
        loadingLabel.setVisible(false);
        loadingLabel.setManaged(false);
    }

    @FXML
    protected void onStartQuizClick() {
        initialDialogueBox.setVisible(false);
        initialDialogueBox.setManaged(false);
        loadingLabel.setVisible(true);
        loadingLabel.setManaged(true);

        new Thread(this::loadQuestion).start();
    }

    private void loadQuestion() {
        try {
            currentQuestion = TriviaAPI.fetchQuestion();
            javafx.application.Platform.runLater(this::displayQuestion);
        } catch (Exception e) {
            javafx.application.Platform.runLater(() -> {
                loadingLabel.setText("Erreur: " + e.getMessage());
            });
        }
    }

    private void displayQuestion() {
        loadingLabel.setVisible(false);
        loadingLabel.setManaged(false);

        questionLabel.setText(currentQuestion.questionText);
        answersBox.getChildren().clear();

        for (String answer : currentQuestion.answers) {
            Button answerButton = new Button(answer);
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
        if (answered) {
            return;
        }

        answered = true;
        boolean isCorrect = selectedAnswer.equals(currentQuestion.correctAnswer);

        if (isCorrect) {
            feedbackLabel.setText("✓ Correct!");
            feedbackLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #4ade80;");
            clickedButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #16a34a; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");
        } else {
            feedbackLabel.setText("✗ Incorrect! La bonne réponse est: " + currentQuestion.correctAnswer);
            feedbackLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ef4444;");
            clickedButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #dc2626; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");

            for (int i = 0; i < answersBox.getChildren().size(); i++) {
                Button btn = (Button) answersBox.getChildren().get(i);
                if (btn.getText().equals(currentQuestion.correctAnswer)) {
                    btn.setStyle("-fx-font-size: 14px; -fx-padding: 10 15 10 15; -fx-background-color: #16a34a; -fx-text-fill: #f8fafc; -fx-border-radius: 8;");
                }
            }
        }

        nextButton.setVisible(true);
        nextButton.setManaged(true);
    }

    @FXML
    protected void onNextClick() {
        goToQuizScreen();
    }

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

    private void goToQuizScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
            Parent quizRoot = loader.load();

            Stage stage = (Stage) gameMenuButton.getScene().getWindow();
            stage.setScene(new Scene(quizRoot, 780, 460));
            stage.setTitle("Java Quizz - Quiz");
        } catch (IOException exception) {
            AppMenuActions.showError("Impossible de charger le quiz.");
        }
    }
}

