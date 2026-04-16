package com.example.javaquizz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final double WINDOW_WIDTH = 780;
    private static final double WINDOW_HEIGHT = 460;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
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
