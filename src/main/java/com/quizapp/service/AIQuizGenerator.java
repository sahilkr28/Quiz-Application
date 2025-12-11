package com.quizapp.service;

import com.quizapp.model.DifficultyLevel;
import okhttp3.*;

public class AIQuizGenerator {

    private static final String GEMINI_URL =
        "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-pro:generateContent";

    private static final String API_KEY = "AIzaSyBSLLAvggwgrhMthDjHmLPmIL8s6zAQuZA";
                                           
    private final OkHttpClient client = new OkHttpClient();

    // TEMPORARY placeholder — prevents QuizService errors
    public java.util.List<com.quizapp.model.Question> generateQuestions(
            String topic, DifficultyLevel level, int count) {

        throw new UnsupportedOperationException(
            "generateQuestions() not implemented yet. Use debugFetchRawGemini().");
    }

    /**
     * DEBUG ONLY: Fetch raw Gemini response and print it.
     */
    public void debugFetchRawGemini(String topic, DifficultyLevel level, int count) {

        try {
            String prompt = "Generate " + count + " MCQs on '" + topic +
                    "' with difficulty " + level.name() + ".";

            String jsonBody = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" +
                    prompt.replace("\"", "\\\"") + "\" } ] } ] }";

            Request request = new Request.Builder()
                    .url(GEMINI_URL + "?key=" + API_KEY)
                    .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                    .build();

            System.out.println("\n===== SENDING REQUEST TO GEMINI =====");
            System.out.println(prompt);

            Response response = client.newCall(request).execute();

            System.out.println("\n===== GEMINI HTTP STATUS =====");
            System.out.println(response.code());

            String body = response.body().string();

            System.out.println("\n===== RAW GEMINI RESPONSE START =====");
            System.out.println(body);
            System.out.println("===== RAW GEMINI RESPONSE END =====\n");

        } catch (Exception e) {
            System.out.println("Error calling Gemini: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AIQuizGenerator gen = new AIQuizGenerator();
        gen.debugFetchRawGemini("Java basics", DifficultyLevel.EASY, 5);
    }
}
