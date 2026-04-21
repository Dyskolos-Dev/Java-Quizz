package com.example.javaquizz;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TriviaAPI {
    private static final String API_URL = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=multiple";

    public static Question fetchQuestion() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Java-Quizz");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if (connection.getResponseCode() != 200) {
            throw new Exception("Erreur API");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray results = jsonResponse.getAsJsonArray("results");
        if (results == null || results.size() == 0) {
            throw new Exception("Question indisponible");
        }
        JsonObject q = results.get(0).getAsJsonObject();

        String questionText = q.get("question").getAsString();
        String correctAnswer = q.get("correct_answer").getAsString();
        JsonArray incorrect = q.getAsJsonArray("incorrect_answers");

        String[] answers = new String[incorrect.size() + 1];
        answers[0] = correctAnswer;
        for (int i = 0; i < incorrect.size(); i++) {
            answers[i + 1] = incorrect.get(i).getAsString();
        }

        shuffleArray(answers);
        return new Question(questionText, correctAnswer, answers);
    }

    private static void shuffleArray(String[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            String temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static class Question {
        public String questionText;
        public String correctAnswer;
        public String[] answers;

        public Question(String questionText, String correctAnswer, String[] answers) {
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
            this.answers = answers;
        }
    }
}


