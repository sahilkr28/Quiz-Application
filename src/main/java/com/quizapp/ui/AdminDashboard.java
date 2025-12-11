package com.quizapp.ui;

import com.quizapp.model.Quiz;
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

import java.util.List;

/**
 * Admin home screen showing quiz management options.
 */
public class AdminDashboard extends BorderPane {

    private final QuizService quizService;
    private final ResultService resultService;
    private final AuthService authService;
    private final User currentUser;

    private final TableView<Quiz> quizTable = new TableView<>();

    public AdminDashboard(Stage stage,
                          User admin,
                          QuizService quizService,
                          ResultService resultService,
                          AuthService authService) {

        this.quizService = quizService;
        this.resultService = resultService;
        this.authService = authService;
        this.currentUser = admin;

        setPadding(new Insets(20));

        Label title = new Label("Admin Dashboard - " + admin.getUsername());
        title.getStyleClass().add("heading");

        Button createQuizBtn = new Button("Create Quiz");
        Button leaderboardBtn = new Button("View Leaderboard");
        Button logoutBtn = new Button("Logout");

        HBox topControls = new HBox(10, createQuizBtn, leaderboardBtn, logoutBtn);
        topControls.setAlignment(Pos.CENTER_LEFT);

        VBox topBox = new VBox(10, title, topControls);
        setTop(topBox);

        // Quiz table
        TableColumn<Quiz, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));

        TableColumn<Quiz, String> diffCol = new TableColumn<>("Difficulty");
        diffCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDifficulty().name()));

        TableColumn<Quiz, Number> timeCol = new TableColumn<>("Time (sec)");
        timeCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTimeLimitSeconds()));

        quizTable.getColumns().addAll(titleCol, diffCol, timeCol);
        refreshQuizTable();

        setCenter(quizTable);

        createQuizBtn.setOnAction(e -> {
            CreateQuizPage createQuizPage = new CreateQuizPage(stage, admin, quizService, resultService, authService);
            stage.setScene(new Scene(createQuizPage, 800, 600));
        });

        leaderboardBtn.setOnAction(e -> {
            Quiz selected = quizTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select a quiz to view its leaderboard.");
                return;
            }
            LeaderboardPage lb = new LeaderboardPage(stage, selected, resultService, quizService, authService, admin);
            stage.setScene(new Scene(lb, 800, 600));
        });

        logoutBtn.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage, authService, quizService, resultService);
            stage.setScene(new Scene(loginPage, 800, 600));
        });
    }

    private void refreshQuizTable() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        quizTable.setItems(FXCollections.observableArrayList(quizzes));
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.showAndWait();
    }
}
