package com.quizapp.ui;

import com.quizapp.model.Quiz;
import com.quizapp.model.User;
import com.quizapp.service.AuthService;
import com.quizapp.service.QuizService;
import com.quizapp.service.ResultService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Displays quiz result summary after submission.
 */
public class QuizSummaryPage extends BorderPane {

    public QuizSummaryPage(Stage stage,
                           User user,
                           Quiz quiz,
                           int score,
                           int totalQuestions,
                           int timeTakenSeconds,
                           QuizService quizService,
                           ResultService resultService,
                           AuthService authService) {

        setPadding(new Insets(20));

        Label heading = new Label("Quiz Summary");
        heading.getStyleClass().add("heading");

        Label quizLabel = new Label("Quiz: " + quiz.getTitle());
        Label scoreLabel = new Label("Score: " + score + " / " + totalQuestions);
        Label timeLabel = new Label("Time Taken: " + timeTakenSeconds + " seconds");

        Button backBtn = new Button("Back to Dashboard");
        Button leaderboardBtn = new Button("View Leaderboard for this Quiz");

        VBox centerBox = new VBox(10, heading, quizLabel, scoreLabel, timeLabel, leaderboardBtn, backBtn);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));

        setCenter(centerBox);

        backBtn.setOnAction(e -> {
            UserDashboard ud = new UserDashboard(stage, user, quizService, resultService, authService);
            stage.setScene(new Scene(ud, 800, 600));
        });

        leaderboardBtn.setOnAction(e -> {
            LeaderboardPage lb = new LeaderboardPage(stage, quiz, resultService, quizService, authService, user);
            stage.setScene(new Scene(lb, 800, 600));
        });
    }
}
