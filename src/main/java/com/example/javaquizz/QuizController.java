package com.example.javaquizz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
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
import java.util.List;

public class QuizController {
    private static final String DEFUSE_CODE = "1406";

    @FXML
    private MenuButton gameMenuButton;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private VBox successBox;

    @FXML
    private VBox finalDialogueBox;

    @FXML
    private Label finalDialogueText;

    @FXML
    private Button finalNextButton;

    @FXML
    private VBox defuseBox;

    @FXML
    private VBox defusePad;

    @FXML
    private Label defuseCodeDisplay;

    @FXML
    private Label defuseFeedbackLabel;

    private final StringBuilder enteredCode = new StringBuilder();

    private final List<String> endDialogues = List.of(
            "Kiki: Bravo, tu as reussi a recuperer le code de desamorssage.",
            "Kiki: Viens, on va dans ma cave le desamorcer.",
            "*Vous descendez rapidement les escaliers vers la cave...*",
            "Kiki: On y est. Le panneau est devant toi, tape le code pour terminer."
    );

    private int dialogueIndex = 0;

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

    @FXML
    protected void onTalkToKikiClick() {
        successBox.setVisible(false);
        successBox.setManaged(false);

        finalDialogueBox.setVisible(true);
        finalDialogueBox.setManaged(true);

        defuseBox.setVisible(false);
        defuseBox.setManaged(false);
        setPadDisabled(false);
        resetCodeEntry();
        defuseFeedbackLabel.setText("");

        dialogueIndex = 0;
        finalNextButton.setDisable(false);
        finalNextButton.setText("Suivant");
        setDialogueText();
    }

    @FXML
    protected void onFinalNextClick() {
        if (dialogueIndex < endDialogues.size() - 1) {
            dialogueIndex++;
            setDialogueText();

            if (dialogueIndex == endDialogues.size() - 1) {
                finalNextButton.setText("Desamorcage");
            }
            return;
        }

        defuseBox.setVisible(true);
        defuseBox.setManaged(true);
        finalNextButton.setDisable(true);
        resetCodeEntry();
        setPadDisabled(false);
    }

    @FXML
    protected void onValidateCodeClick() {
        String typedCode = enteredCode.toString();

        if (DEFUSE_CODE.equals(typedCode)) {
            goToEndScreen();
        } else {
            defuseFeedbackLabel.setText("Mauvais code. Reessaie.");
        }
    }

    @FXML
    protected void onDigitClick(ActionEvent event) {
        if (enteredCode.length() >= DEFUSE_CODE.length()) {
            return;
        }

        Button clickedButton = (Button) event.getSource();
        enteredCode.append(clickedButton.getText());
        updateCodeDisplay();
    }

    @FXML
    protected void onBackspaceClick() {
        if (enteredCode.length() == 0) {
            return;
        }

        enteredCode.deleteCharAt(enteredCode.length() - 1);
        updateCodeDisplay();
    }

    @FXML
    protected void onClearClick() {
        resetCodeEntry();
    }

    private void setDialogueText() {
        finalDialogueText.setText(endDialogues.get(dialogueIndex));

        // Switch to cave background when the group reaches the cave.
        if (dialogueIndex >= 3) {
            backgroundImageView.setImage(new Image(HelloApplication.class.getResource("images/kikicave.png").toExternalForm()));
        } else {
            backgroundImageView.setImage(new Image(HelloApplication.class.getResource("images/kikihouse.png").toExternalForm()));
        }
    }

    private void updateCodeDisplay() {
        StringBuilder display = new StringBuilder(enteredCode);
        while (display.length() < DEFUSE_CODE.length()) {
            display.append("-");
        }
        defuseCodeDisplay.setText(display.toString());
    }

    private void resetCodeEntry() {
        enteredCode.setLength(0);
        updateCodeDisplay();
    }

    private void setPadDisabled(boolean disabled) {
        defusePad.setDisable(disabled);
    }

    private void goToEndScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-end-view.fxml"));
            Parent endRoot = loader.load();

            Stage stage = (Stage) gameMenuButton.getScene().getWindow();
            stage.setScene(new Scene(endRoot, 780, 460));
            stage.setTitle("Java Quizz - Fin");
        } catch (IOException exception) {
            AppMenuActions.showError("Impossible de charger l'ecran de fin.");
        }
    }
}
