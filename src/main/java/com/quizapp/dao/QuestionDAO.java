package com.quizapp.dao;

import com.quizapp.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles CRUD operations related to questions.
 */
public class QuestionDAO {

    public void addQuestion(Question q) {
        String sql = """
            INSERT INTO questions(quiz_id, question_text, option_a, option_b, option_c, option_d, correct_index)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, q.getQuizId());
            stmt.setString(2, q.getQuestionText());
            stmt.setString(3, q.getOptions().get(0));
            stmt.setString(4, q.getOptions().get(1));
            stmt.setString(5, q.getOptions().get(2));
            stmt.setString(6, q.getOptions().get(3));
            stmt.setInt(7, q.getCorrectIndex());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        List<Question> questions = new ArrayList<>();

        String sql = "SELECT * FROM questions WHERE quiz_id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                List<String> opts = new ArrayList<>();
                opts.add(rs.getString("option_a"));
                opts.add(rs.getString("option_b"));
                opts.add(rs.getString("option_c"));
                opts.add(rs.getString("option_d"));

                Question q = new Question(
                        rs.getInt("id"),
                        rs.getInt("quiz_id"),
                        rs.getString("question_text"),
                        opts,
                        rs.getInt("correct_index")
                );

                questions.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
