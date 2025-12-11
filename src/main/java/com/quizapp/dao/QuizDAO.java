package com.quizapp.dao;

import com.quizapp.model.DifficultyLevel;
import com.quizapp.model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles CRUD operations related to quizzes.
 */
public class QuizDAO {

    public int addQuiz(Quiz quiz) {
        String sql = "INSERT INTO quizzes(title, description, difficulty, time_limit) VALUES(?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, quiz.getTitle());
            stmt.setString(2, quiz.getDescription());
            stmt.setString(3, quiz.getDifficulty().name());
            stmt.setInt(4, quiz.getTimeLimitSeconds());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Quiz quiz = new Quiz(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        DifficultyLevel.valueOf(rs.getString("difficulty")),
                        rs.getInt("time_limit")
                );
                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizzes;
    }
}
