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
 * Dashboard for normal users to choose and attempt quizzes.
 */
public class UserDashboard extends BorderPane {

    private final QuizService quizService;
    private final ResultService resultService;
    private final AuthService authService;
    private final User currentUser;

    private final TableView<Quiz> quizTable = new TableView<>();

    public UserDashboard(Stage stage,
                         User user,
                         QuizService quizService,
                         ResultService resultService,
                         AuthService authService) {

        this.quizService = quizService;
        this.resultService = resultService;
        this.authService = authService;
        this.currentUser = user;

        setPadding(new Insets(20));

        Label heading = new Label("User Dashboard - " + user.getUsername());
        heading.getStyleClass().add("heading");

        Button startQuizBtn = new Button("Start Selected Quiz");
        Button logoutBtn = new Button("Logout");

        HBox controls = new HBox(10, startQuizBtn, logoutBtn);
        controls.setAlignment(Pos.CENTER_LEFT);

        VBox topBox = new VBox(10, heading, controls);
        setTop(topBox);

        TableColumn<Quiz, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));

        TableColumn<Quiz, String> diffCol = new TableColumn<>("Difficulty");
        diffCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDifficulty().name()));

        TableColumn<Quiz, Number> timeCol = new TableColumn<>("Time (sec)");
        timeCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getTimeLimitSeconds()));

        quizTable.getColumns().addAll(titleCol, diffCol, timeCol);
        refreshQuizTable();

        setCenter(quizTable);

        startQuizBtn.setOnAction(e -> {
            Quiz selected = quizTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select a quiz to start.");
                return;
            }

            QuizPlayer qp = new QuizPlayer(stage, currentUser, selected, quizService, resultService, authService);
            stage.setScene(new Scene(qp, 800, 600));
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
