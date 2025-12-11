package com.quizapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single multiple-choice question inside a quiz.
 */
public class Question {

    private int id;
    private int quizId;
    private String questionText;
    private List<String> options;
    private int correctIndex; // 0-based index of correct option

    public Question() {
        this.options = new ArrayList<>();
    }

    public Question(int id, int quizId, String questionText, List<String> options, int correctIndex) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.options = options != null ? options : new ArrayList<>();
        this.correctIndex = correctIndex;
    }

    public Question(int quizId, String questionText, List<String> options, int correctIndex) {
        this.quizId = quizId;
        this.questionText = questionText;
        this.options = options != null ? options : new ArrayList<>();
        this.correctIndex = correctIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options != null ? options : new ArrayList<>();
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }
}
