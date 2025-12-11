package com.quizapp.ui;

import com.quizapp.model.Question;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen where user attempts a quiz with timer.
 * Shows all questions with radio buttons.
 */
public class QuizPlayer extends BorderPane {

    private final List<Question> questions;
    private final List<ToggleGroup> answerGroups = new ArrayList<>();

    private int timeRemaining;
    private Timeline timeline;

    public QuizPlayer(Stage stage,
                      User user,
                      Quiz quiz,
                      QuizService quizService,
                      ResultService resultService,
                      AuthService authService) {

        setPadding(new Insets(20));

        Label heading = new Label("Quiz: " + quiz.getTitle());
        heading.getStyleClass().add("heading");

        Label timerLabel = new Label();
        this.timeRemaining = quiz.getTimeLimitSeconds();

        HBox topBox = new HBox(20, heading, timerLabel);
        topBox.setAlignment(Pos.CENTER_LEFT);
        setTop(topBox);

        this.questions = quizService.getQuestionsByQuiz(quiz.getId());

        VBox questionsBox = new VBox(15);
        questionsBox.setPadding(new Insets(10));

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            Label qLabel = new Label((i + 1) + ". " + q.getQuestionText());

            ToggleGroup tg = new ToggleGroup();
            answerGroups.add(tg);

            VBox optionsBox = new VBox(5);
            for (int optIndex = 0; optIndex < q.getOptions().size(); optIndex++) {
                RadioButton rb = new RadioButton(q.getOptions().get(optIndex));
                rb.setToggleGroup(tg);
                rb.setUserData(optIndex);
                optionsBox.getChildren().add(rb);
            }

            VBox qBlock = new VBox(5, qLabel, optionsBox);
            qBlock.setPadding(new Insets(5));
            qBlock.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 5;");
            questionsBox.getChildren().add(qBlock);
        }

        ScrollPane scrollPane = new ScrollPane(questionsBox);
        scrollPane.setFitToWidth(true);
        setCenter(scrollPane);

        Button submitBtn = new Button("Submit Quiz");
        Button cancelBtn = new Button("Cancel");

        HBox bottomBox = new HBox(10, submitBtn, cancelBtn);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        setBottom(bottomBox);

        // Timer
        updateTimerLabel(timerLabel);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeRemaining--;
                    updateTimerLabel(timerLabel);
                    if (timeRemaining <= 0) {
                        timeline.stop();
                        submitQuiz(stage, user, quiz, resultService, quizService, authService);
                    }
                })
        );
        timeline.setCycleCount(quiz.getTimeLimitSeconds());
        timeline.play();

        submitBtn.setOnAction(e -> {
            if (timeline != null) {
                timeline.stop();
            }
            submitQuiz(stage, user, quiz, resultService, quizService, authService);
        });

        cancelBtn.setOnAction(e -> {
            if (timeline != null) {
                timeline.stop();
            }
            UserDashboard ud = new UserDashboard(stage, user, quizService, resultService, authService);
            stage.setScene(new Scene(ud, 800, 600));
        });
    }

    private void updateTimerLabel(Label timerLabel) {
        int mins = timeRemaining / 60;
        int secs = timeRemaining % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", mins, secs));
    }

    private void submitQuiz(Stage stage,
                            User user,
                            Quiz quiz,
                            ResultService resultService,
                            QuizService quizService,
                            AuthService authService) {

        List<Integer> userAnswers = new ArrayList<>();
        for (ToggleGroup tg : answerGroups) {
            Toggle selected = tg.getSelectedToggle();
            if (selected == null) {
                userAnswers.add(-1);
            } else {
                userAnswers.add((Integer) selected.getUserData());
            }
        }

        int score = resultService.calculateScore(questions, userAnswers);
        int total = questions.size();
        int timeTaken = quiz.getTimeLimitSeconds() - timeRemaining;

        resultService.saveResult(user.getId(), quiz.getId(), score, total, timeTaken);

        QuizSummaryPage summary = new QuizSummaryPage(stage, user, quiz, score, total, timeTaken, quizService, resultService, authService);
        stage.setScene(new Scene(summary, 800, 600));
    }
}
