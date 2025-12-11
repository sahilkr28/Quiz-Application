package com.quizapp.model;

import java.time.LocalDateTime;

/**
 * Represents a user's result for a quiz attempt.
 */
public class Result {

    private int id;
    private int userId;
    private int quizId;
    private int score;
    private int totalQuestions;
    private int timeTakenSeconds;
    private LocalDateTime dateAttempted;

    public Result() {
    }

    public Result(int id, int userId, int quizId, int score, int totalQuestions,
                  int timeTakenSeconds, LocalDateTime dateAttempted) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.timeTakenSeconds = timeTakenSeconds;
        this.dateAttempted = dateAttempted;
    }

    public Result(int userId, int quizId, int score, int totalQuestions,
                  int timeTakenSeconds, LocalDateTime dateAttempted) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.timeTakenSeconds = timeTakenSeconds;
        this.dateAttempted = dateAttempted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(int timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public LocalDateTime getDateAttempted() {
        return dateAttempted;
    }

    public void setDateAttempted(LocalDateTime dateAttempted) {
        this.dateAttempted = dateAttempted;
    }
}
