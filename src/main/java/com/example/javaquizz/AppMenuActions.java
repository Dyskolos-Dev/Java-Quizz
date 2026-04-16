package com.example.javaquizz;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AppMenuActions {
    private AppMenuActions() {
    }

    public static void showCredits() {
        Alert credits = new Alert(
                Alert.AlertType.INFORMATION,
                "Java Quizz\n\nConcept: Bombe a desamorcer via quiz\nRealisation: Dyskolos_ & PrettyFlacko",
                ButtonType.OK
        );
        credits.setTitle("Credits");
        credits.setHeaderText("Credits");
        credits.showAndWait();
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
