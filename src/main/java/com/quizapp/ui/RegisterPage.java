package com.quizapp.ui;

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
 * Registration screen for new users.
 */
public class RegisterPage extends BorderPane {

    public RegisterPage(Stage stage,
                        AuthService authService,
                        QuizService quizService,
                        ResultService resultService) {

        setPadding(new Insets(20));

        Label title = new Label("Register New Account");
        title.getStyleClass().add("heading");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("USER", "ADMIN");
        roleBox.setValue("USER");

        Button registerBtn = new Button("Register");
        Button backToLoginBtn = new Button("Back to Login");

        Label messageLabel = new Label();

        VBox form = new VBox(10,
                title,
                usernameField,
                passwordField,
                confirmPasswordField,
                roleBox,
                registerBtn,
                backToLoginBtn,
                messageLabel);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(300);

        setCenter(form);

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String p1 = passwordField.getText().trim();
            String p2 = confirmPasswordField.getText().trim();
            String role = roleBox.getValue();

            if (username.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
                messageLabel.setText("All fields are required.");
                return;
            }
            if (!p1.equals(p2)) {
                messageLabel.setText("Passwords do not match.");
                return;
            }

            boolean success = authService.register(username, p1, role);
            if (!success) {
                messageLabel.setText("Username already exists.");
            } else {
                messageLabel.setText("Registered successfully. Go to login.");
            }
        });

        backToLoginBtn.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage, authService, quizService, resultService);
            stage.setScene(new Scene(loginPage, 800, 600));
        });
    }
}
