package com.example.javaquizz;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    private static final double WINDOW_WIDTH = 780;
    private static final double WINDOW_HEIGHT = 460;
    private static HostServices hostServices;

    public static HostServices getAppHostServices() {
        return hostServices;
    }

    @Override
    public void start(Stage stage) throws IOException {
        hostServices = getHostServices();

        URL resource = HelloApplication.class.getResource("/com/example/javaquizz/hello-view.fxml");
        if (resource == null) {
            throw new IOException("Ressource hello-view.fxml introuvable");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);

        stage.setTitle("Java Quizz - Introduction");
        stage.setScene(scene);

        // Lock window size and prevent fullscreen/maximize state.
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.fullScreenProperty().addListener((obs, wasFullScreen, isFullScreen) -> {
            if (isFullScreen) {
                stage.setFullScreen(false);
            }
        });

        stage.show();
    }
}
