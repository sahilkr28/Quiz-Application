package com.quizapp.ui;

import com.quizapp.model.Quiz;
import com.quizapp.model.Result;
import com.quizapp.model.User;
import com.quizapp.service.AuthService;
import com.quizapp.service.QuizService;
import com.quizapp.service.ResultService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Shows leaderboard for a given quiz.
 */
public class LeaderboardPage extends BorderPane {

    public LeaderboardPage(Stage stage,
                           Quiz quiz,
                           ResultService resultService,
                           QuizService quizService,
                           AuthService authService,
                           User currentUser) {

        setPadding(new Insets(20));

        Label heading = new Label("Leaderboard - " + quiz.getTitle());
        heading.getStyleClass().add("heading");

        TableView<Result> table = new TableView<>();

        TableColumn<Result, Number> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getScore()));

        TableColumn<Result, Number> totalCol = new TableColumn<>("Total Q");
        totalCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTotalQuestions()));

        TableColumn<Result, Number> timeCol = new TableColumn<>("Time Taken (sec)");
        timeCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTimeTakenSeconds()));

        TableColumn<Result, String> dateCol = new TableColumn<>("Attempted On");
        dateCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDateAttempted().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

        table.getColumns().addAll(scoreCol, totalCol, timeCol, dateCol);

        List<Result> results = resultService.getLeaderboard(quiz.getId());
        table.setItems(FXCollections.observableArrayList(results));

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                AdminDashboard dash = new AdminDashboard(stage, currentUser, quizService, resultService, authService);
                stage.setScene(new Scene(dash, 800, 600));
            } else {
                UserDashboard ud = new UserDashboard(stage, currentUser, quizService, resultService, authService);
                stage.setScene(new Scene(ud, 800, 600));
            }
        });

        VBox centerBox = new VBox(10, heading, table, backBtn);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(10));
        setCenter(centerBox);
    }
}
