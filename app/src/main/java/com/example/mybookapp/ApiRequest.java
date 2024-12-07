package com.example.mybookapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRequest {

    public String makeConnection(String title, String author, String isbn) {
        try {
            String apiUrl = buildURL(title, author, isbn);
            System.out.println("API URL: " + apiUrl);

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            return parseResponse(conn);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildURL(String title, String author, String isbn) {
        StringBuilder apiUrlBuilder = new StringBuilder("https://openlibrary.org/search.json?");

        try {
            if (!title.isEmpty()) {
                apiUrlBuilder.append("title=")
                        .append(URLEncoder.encode(title, StandardCharsets.UTF_8.toString()))
                        .append("&");
            }
            if (!author.isEmpty()) {
                apiUrlBuilder.append("author=")
                        .append(URLEncoder.encode(author, StandardCharsets.UTF_8.toString()))
                        .append("&");
            }
            if (!isbn.isEmpty()) {
                apiUrlBuilder.append("isbn=")
                        .append(URLEncoder.encode(isbn, StandardCharsets.UTF_8.toString()))
                        .append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String apiUrl = apiUrlBuilder.toString();
        if (apiUrl.endsWith("&")) {
            apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
        }

        return apiUrl;
    }

    private String parseResponse(HttpURLConnection conn) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
