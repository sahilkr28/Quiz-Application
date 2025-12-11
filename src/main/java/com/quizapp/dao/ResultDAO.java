package com.quizapp.dao;

import com.quizapp.model.Result;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles quiz attempt result storage and leaderboard fetching.
 */
public class ResultDAO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public void saveResult(Result result) {
        String sql = """
            INSERT INTO results(user_id, quiz_id, score, total_questions, time_taken, date_attempted)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, result.getUserId());
            stmt.setInt(2, result.getQuizId());
            stmt.setInt(3, result.getScore());
            stmt.setInt(4, result.getTotalQuestions());
            stmt.setInt(5, result.getTimeTakenSeconds());
            stmt.setString(6, result.getDateAttempted().format(FORMATTER));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getLeaderboard(int quizId) {
        List<Result> results = new ArrayList<>();

        String sql = """
            SELECT * FROM results
            WHERE quiz_id = ?
            ORDER BY score DESC, time_taken ASC
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Result r = new Result(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("score"),
                        rs.getInt("total_questions"),
                        rs.getInt("time_taken"),
                        LocalDateTime.parse(rs.getString("date_attempted"), FORMATTER)
                );

                results.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}
