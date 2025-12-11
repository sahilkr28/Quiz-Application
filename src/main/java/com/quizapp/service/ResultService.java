package com.quizapp.service;

import com.quizapp.dao.ResultDAO;
import com.quizapp.model.Question;
import com.quizapp.model.Result;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles scoring logic and saving quiz results.
 */
public class ResultService {

    private final ResultDAO resultDAO = new ResultDAO();

    /**
     * Calculates score from user answers.
     * @param userAnswers index of chosen options.
     */
    public int calculateScore(List<Question> questions, List<Integer> userAnswers) {
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i) == questions.get(i).getCorrectIndex()) {
                score++;
            }
        }

        return score;
    }

    /**
     * Saves result into DB.
     */
    public void saveResult(int userId, int quizId, int score, int totalQ, int timeTakenSeconds) {
        Result result = new Result(
                userId,
                quizId,
                score,
                totalQ,
                timeTakenSeconds,
                LocalDateTime.now()
        );

        resultDAO.saveResult(result);
    }

    public List<Result> getLeaderboard(int quizId) {
        return resultDAO.getLeaderboard(quizId);
    }
}
