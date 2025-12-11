package com.quizapp.ui;

import com.quizapp.model.Question;
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

import java.util.Arrays;

/**
 * Allows admin to add manual questions to a quiz.
 */
public class QuestionEditor extends BorderPane {

    public QuestionEditor(Stage stage,
                          User admin,
                          int quizId,
                          QuizService quizService,
                          ResultService resultService,
                          AuthService authService) {

        setPadding(new Insets(20));

        Label heading = new Label("Question Editor - Quiz ID: " + quizId);
        heading.getStyleClass().add("heading");

        TextArea questionArea = new TextArea();
        questionArea.setPromptText("Enter question text here...");

        TextField optionA = new TextField();
        optionA.setPromptText("Option A");
        TextField optionB = new TextField();
        optionB.setPromptText("Option B");
        TextField optionC = new TextField();
        optionC.setPromptText("Option C");
        TextField optionD = new TextField();
        optionD.setPromptText("Option D");

        ComboBox<String> correctBox = new ComboBox<>();
        correctBox.getItems().addAll("0 - Option A", "1 - Option B", "2 - Option C", "3 - Option D");
        correctBox.setValue("0 - Option A");

        Button addQuestionBtn = new Button("Add Question");
        Button finishBtn = new Button("Finish & Go Back");

        Label msgLabel = new Label();

        VBox form = new VBox(10,
                heading,
                new Label("Question:"),
                questionArea,
                new Label("Options:"),
                optionA,
                optionB,
                optionC,
                optionD,
                new Label("Correct Option Index:"),
                correctBox,
                addQuestionBtn,
                msgLabel,
                finishBtn
        );
        form.setAlignment(Pos.TOP_LEFT);
        form.setPadding(new Insets(10));
        setCenter(form);

        addQuestionBtn.setOnAction(e -> {
            try {
                String qText = questionArea.getText().trim();
                String a = optionA.getText().trim();
                String b = optionB.getText().trim();
                String c = optionC.getText().trim();
                String d = optionD.getText().trim();

                if (qText.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
                    msgLabel.setText("All fields are required.");
                    return;
                }

                int correctIndex = Integer.parseInt(correctBox.getValue().substring(0, 1));

                Question q = new Question();
                q.setQuizId(quizId);
                q.setQuestionText(qText);
                q.setOptions(Arrays.asList(a, b, c, d));
                q.setCorrectIndex(correctIndex);

                quizService.addManualQuestion(q);
                msgLabel.setText("Question added.");

                questionArea.clear();
                optionA.clear();
                optionB.clear();
                optionC.clear();
                optionD.clear();

            } catch (Exception ex) {
                msgLabel.setText("Error adding question.");
            }
        });

        finishBtn.setOnAction(e -> {
            AdminDashboard dash = new AdminDashboard(stage, admin, quizService, resultService, authService);
            stage.setScene(new Scene(dash, 800, 600));
        });
    }
}
