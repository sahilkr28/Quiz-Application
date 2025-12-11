package com.quizapp.service;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.dao.QuizDAO;
import com.quizapp.model.DifficultyLevel;
import com.quizapp.model.Question;
import com.quizapp.model.Quiz;

import java.util.List;

/**
 * Handles quiz creation, loading, AI quiz generation and manual question operations.
 */
public class QuizService {

    private final QuizDAO quizDAO = new QuizDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();
    private final AIQuizGenerator aiGenerator = new AIQuizGenerator();

    /**
     * Creates an empty quiz and returns its generated ID.
     */
    public int createQuiz(String title, String desc, DifficultyLevel level, int timeLimitSeconds) {
        Quiz quiz = new Quiz(title, desc, level, timeLimitSeconds);
        return quizDAO.addQuiz(quiz);
    }

    /**
     * Saves a manually created question.
     */
    public void addManualQuestion(Question q) {
        questionDAO.addQuestion(q);
    }

    /**
     * Generates questions using Gemini, then stores them.
     */
    public void addAIQuestions(int quizId, String topic, DifficultyLevel level, int count) throws Exception {
        List<Question> questions = aiGenerator.generateQuestions(topic, level, count);

        for (Question q : questions) {
            q.setQuizId(quizId);
            questionDAO.addQuestion(q);
        }
    }

    public List<Quiz> getAllQuizzes() {
        return quizDAO.getAllQuizzes();
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        return questionDAO.getQuestionsByQuiz(quizId);
    }
}

