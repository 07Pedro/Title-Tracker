package com.example.mybookapp.apiRequest;

import com.example.mybookapp.parsing.Book;
import com.example.mybookapp.parsing.BookParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiRequest {

    public void makeConnectionAsync(String title, String author, String isbn) {
        new Thread(() -> {
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

                try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    List<Book> books = BookParser.parseBooks(responseReader);

                    handleBooks(books);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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

    private void handleBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Handling Books: " + books.size());
            books.forEach(System.out::println);
        }
    }
}
