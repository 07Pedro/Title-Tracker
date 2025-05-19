package com.example.mybookapp.apiRequest;

import android.os.Handler;
import android.os.Looper;

import com.example.mybookapp.parsing.Book;
import com.example.mybookapp.parsing.BookParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiRequest {

    public interface OnBooksFetchedListener {
        void onBooksFetched(List<Book> books);
    }
    private OnBooksFetchedListener booksFetchedListener;
    public void setOnBooksFetchedListener(OnBooksFetchedListener listener) {
        this.booksFetchedListener = listener;
    }

    public void makeConnectionAsync(String title, String author, String isbn) {
        new Thread(() -> {
            try {
                // Fetch the API response as a Reader
                Reader apiResponseReader = fetchApiResponse(title, author, isbn);

                if (apiResponseReader == null) {
                    System.err.println("API response is null. Cannot parse books.");
                    return;
                }

                // Parse the books from the API response
                List<Book> books = BookParser.parseBooks(apiResponseReader);

                // Handle the parsed books (e.g., display them in the UI)
                handleBooks(books);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private Reader fetchApiResponse(String title, String author, String isbn) throws IOException {
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

        return new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
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
        System.out.println("ISBN value: " + isbn);


        return apiUrl;
    }

    private void handleBooks(List<Book> books) {
        if (booksFetchedListener != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (books.isEmpty()) {
                    System.out.println("No books found.");
                } else {
                    System.out.println("Handling Books: " + books.size());
                    books.forEach(System.out::println);
                }
                booksFetchedListener.onBooksFetched(books);
            });
        }
    }
}
