package com.quizapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quiz that contains multiple questions.
 */
public class Quiz {

    private int id;
    private String title;
    private String description;
    private DifficultyLevel difficulty;
    private int timeLimitSeconds; // total time to attempt this quiz
    private List<Question> questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public Quiz(int id, String title, String description, DifficultyLevel difficulty, int timeLimitSeconds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.timeLimitSeconds = timeLimitSeconds;
        this.questions = new ArrayList<>();
    }

    public Quiz(String title, String description, DifficultyLevel difficulty, int timeLimitSeconds) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.timeLimitSeconds = timeLimitSeconds;
        this.questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(int timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
    }

    public void addQuestion(Question question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        this.questions.add(question);
    }
}
