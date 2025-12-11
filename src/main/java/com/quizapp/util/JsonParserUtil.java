package com.quizapp.util;

import com.google.gson.*;
import com.quizapp.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParserUtil {

    public static String extractJsonBlock(String geminiResponse) {

        Pattern codeFence = Pattern.compile("```json(.*?)```", Pattern.DOTALL);
        Matcher m1 = codeFence.matcher(geminiResponse);
        if (m1.find()) {
            return m1.group(1).trim();
        }

        Pattern arrayPattern = Pattern.compile("\\[(.*?)]", Pattern.DOTALL);
        Matcher m2 = arrayPattern.matcher(geminiResponse);
        if (m2.find()) {
            return "[" + m2.group(1).trim() + "]";
        }

        try {
            JsonObject root = JsonParser.parseString(geminiResponse).getAsJsonObject();

            JsonArray parts = root.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts");

            String text = parts.get(0).getAsJsonObject().get("text").getAsString();
            return text.trim();

        } catch (Exception ignore) {}

        System.out.println("Could not extract JSON block.");
        return null;
    }

    public static List<Question> parseQuestions(String jsonArrayString) {
        List<Question> list = new ArrayList<>();

        try {
            JsonArray arr = JsonParser.parseString(jsonArrayString).getAsJsonArray();

            for (JsonElement e : arr) {
                JsonObject obj = e.getAsJsonObject();

                String question = obj.get("question").getAsString();
                JsonArray optArr = obj.getAsJsonArray("options");

                List<String> options = new ArrayList<>();
                for (JsonElement o : optArr) {
                    options.add(o.getAsString());
                }

                int correctIndex = obj.get("correctIndex").getAsInt();

                Question q = new Question();
                q.setQuestionText(question);
                q.setOptions(options);
                q.setCorrectIndex(correctIndex);

                list.add(q);
            }

        } catch (Exception ex) {
            System.out.println("Parsing failed: " + ex.getMessage());
            System.out.println(jsonArrayString);
        }

        return list;
    }
}
