package com.quizapp.ui;

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
 * Login screen for existing users.
 */
public class LoginPage extends BorderPane {

    public LoginPage(Stage stage,
                     AuthService authService,
                     QuizService quizService,
                     ResultService resultService) {

        setPadding(new Insets(20));

        Label title = new Label("Quiz Platform - Login");
        title.getStyleClass().add("heading");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button gotoRegisterBtn = new Button("Register");

        Label messageLabel = new Label();

        VBox form = new VBox(10, title, usernameField, passwordField, loginBtn, gotoRegisterBtn, messageLabel);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(300);

        setCenter(form);

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter username and password.");
                return;
            }

            User user = authService.login(username, password);
            if (user == null) {
                messageLabel.setText("Invalid credentials.");
                return;
            }

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                AdminDashboard adminDashboard = new AdminDashboard(stage, user, quizService, resultService, authService);
                stage.setScene(new Scene(adminDashboard, 800, 600));
            } else {
                UserDashboard userDashboard = new UserDashboard(stage, user, quizService, resultService, authService);
                stage.setScene(new Scene(userDashboard, 800, 600));
            }
        });

        gotoRegisterBtn.setOnAction(e -> {
            RegisterPage registerPage = new RegisterPage(stage, authService, quizService, resultService);
            stage.setScene(new Scene(registerPage, 800, 600));
        });
    }
}
