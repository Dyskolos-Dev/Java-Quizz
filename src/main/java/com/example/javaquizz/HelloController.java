package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    private Label dialogueText;

    @FXML
    private Button nextButton;

    private final List<String> dialogues = List.of(
            "Kiki: QU'est ce que tu fous chez moi jeune Zukuk ?",
            "Kiki: Attends... ce bip dans ma cave, c'est pas mon micro-ondes. Il y a une bombe chez moi !",
            "Kiki: Le desamorceur est verrouille par un systeme de questions. Si tu rates, le minuteur accelere.",
            "Kiki: Reponds juste, vite, et on sauve ma maison. Allez, le quiz commence maintenant !"
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
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
            Parent quizRoot = loader.load();

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(quizRoot, 780, 460));
            stage.setTitle("Java Quizz - Quiz");
        } catch (IOException exception) {
            dialogueText.setText("Impossible de charger le quiz pour le moment.");
            nextButton.setDisable(true);
            nextButton.setText("Erreur");
        }
    }
}
