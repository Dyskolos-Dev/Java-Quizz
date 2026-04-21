package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class HelloController {
    @FXML
    private Label dialogueText;

    @FXML
    private Button nextButton;

    private final List<String> dialogues = List.of(
            "Kiki: QU'est ce que tu fous chez moi jeune Zukuk ?",
            "Kiki: Attends... ce bip dans ma cave, c'est pas mon micro-ondes. Il y a une bombe chez moi !",
            "Kiki: Le desamorceur est verrouille par un systeme de questions. Il faut valider 5 bonnes reponses.",
            "Kiki: Reponds juste, vite, et on sauve ma maison. Si tu te trompes, recommence et vise 5/5."
    );

    private int currentDialogueIndex = 0;

    @FXML
    public void initialize() {
        dialogueText.setText(dialogues.get(currentDialogueIndex));
    }

    @FXML
    protected void onNextButtonClick() {
        if (currentDialogueIndex < dialogues.size() - 1) {
            currentDialogueIndex++;
            dialogueText.setText(dialogues.get(currentDialogueIndex));

            if (currentDialogueIndex == dialogues.size() - 1) {
                nextButton.setText("Terminer");
            }
            return;
        }

        goToQuizScreen();
    }

    @FXML
    protected void onRestartClick() {
        currentDialogueIndex = 0;
        dialogueText.setText(dialogues.get(currentDialogueIndex));
        nextButton.setDisable(false);
        nextButton.setText("Suivant");
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
            URL resource = HelloApplication.class.getResource("/com/example/javaquizz/trivia-question-view.fxml");
            if (resource == null) {
                throw new IOException("Ressource trivia-question-view.fxml introuvable");
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Parent quizRoot = loader.load();

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(quizRoot, 780, 460));
            stage.setTitle("Java Quizz - Quiz");
        } catch (Exception exception) {
            exception.printStackTrace();
            dialogueText.setText("Impossible de charger le quiz");
            nextButton.setDisable(true);
            nextButton.setText("Erreur");
        }
    }
}
