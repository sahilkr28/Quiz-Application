package com.quizapp.ui;

import com.quizapp.model.DifficultyLevel;
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
 * Screen where admin creates a quiz, manually or with AI assistance.
 */
public class CreateQuizPage extends BorderPane {

    public CreateQuizPage(Stage stage,
                          User admin,
                          QuizService quizService,
                          ResultService resultService,
                          AuthService authService) {

        setPadding(new Insets(20));

        Label heading = new Label("Create Quiz");
        heading.getStyleClass().add("heading");

        TextField titleField = new TextField();
        titleField.setPromptText("Quiz Title");

        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);

        ComboBox<DifficultyLevel> difficultyBox = new ComboBox<>();
        difficultyBox.getItems().addAll(DifficultyLevel.values());
        difficultyBox.setValue(DifficultyLevel.MEDIUM);

        TextField timeLimitField = new TextField();
        timeLimitField.setPromptText("Time Limit (seconds)");

        // Manual section
        Button createManualBtn = new Button("Create Quiz (Manual Questions)");

        // AI section
        Label aiLabel = new Label("AI Question Generation (Gemini)");
        TextField topicField = new TextField();
        topicField.setPromptText("Topic (e.g., Java OOP)");

        TextField aiCountField = new TextField();
        aiCountField.setPromptText("Number of Questions (e.g., 10)");

        Button createWithAIBtn = new Button("Create Quiz & Generate Questions with AI");

        Button backBtn = new Button("Back");

        Label msgLabel = new Label();

        VBox form = new VBox(10,
                heading,
                new Label("Title:"), titleField,
                new Label("Description:"), descArea,
                new Label("Difficulty:"), difficultyBox,
                new Label("Time Limit (sec):"), timeLimitField,
                new Separator(),
                new Label("Manual Creation:"),
                createManualBtn,
                new Separator(),
                aiLabel,
                new Label("Topic:"), topicField,
                new Label("Number of AI Questions:"), aiCountField,
                createWithAIBtn,
                msgLabel,
                backBtn
        );

        form.setAlignment(Pos.TOP_LEFT);
        form.setPadding(new Insets(10));
        setCenter(form);

        createManualBtn.setOnAction(e -> {
            try {
                String title = titleField.getText().trim();
                String desc = descArea.getText().trim();
                DifficultyLevel level = difficultyBox.getValue();
                int timeLimit = Integer.parseInt(timeLimitField.getText().trim());

                int quizId = quizService.createQuiz(title, desc, level, timeLimit);
                msgLabel.setText("Quiz created. Add questions now.");

                QuestionEditor editor = new QuestionEditor(stage, admin, quizId, quizService, resultService, authService);
                stage.setScene(new Scene(editor, 800, 600));

            } catch (Exception ex) {
                msgLabel.setText("Error creating quiz. Check inputs.");
            }
        });

        createWithAIBtn.setOnAction(e -> {
            try {
                String title = titleField.getText().trim();
                String desc = descArea.getText().trim();
                DifficultyLevel level = difficultyBox.getValue();
                int timeLimit = Integer.parseInt(timeLimitField.getText().trim());
                String topic = topicField.getText().trim();
                int count = Integer.parseInt(aiCountField.getText().trim());

                if (topic.isEmpty()) {
                    msgLabel.setText("Topic is required for AI generation.");
                    return;
                }

                int quizId = quizService.createQuiz(title, desc, level, timeLimit);

                // Generate AI questions & save
                quizService.addAIQuestions(quizId, topic, level, count);
                msgLabel.setText("Quiz created and AI questions generated.");

                QuestionEditor editor = new QuestionEditor(stage, admin, quizId, quizService, resultService, authService);
                stage.setScene(new Scene(editor, 800, 600));

            } catch (Exception ex) {
                ex.printStackTrace();
                msgLabel.setText("Error using AI generation: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> {
            AdminDashboard dash = new AdminDashboard(stage, admin, quizService, resultService, authService);
            stage.setScene(new Scene(dash, 800, 600));
        });
    }
}
