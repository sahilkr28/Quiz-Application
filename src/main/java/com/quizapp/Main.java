package com.quizapp;

import com.quizapp.service.AuthService;
import com.quizapp.service.QuizService;
import com.quizapp.service.ResultService;
import com.quizapp.ui.LoginPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the Quiz Platform Application.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Instantiate services (used across the whole app)
        AuthService authService = new AuthService();
        QuizService quizService = new QuizService();
        ResultService resultService = new ResultService();

        // First page shown = Login Page
        LoginPage loginPage = new LoginPage(stage, authService, quizService, resultService);

        Scene scene = new Scene(loginPage, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("AI-Augmented Quiz Platform");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
