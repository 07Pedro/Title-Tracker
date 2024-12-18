package com.example.mybookapp.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class BookParser {

    public static List<Book> parseBooks(Reader reader) {
        List<Book> books = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(reader);

            JsonNode docsNode = rootNode.get("docs");

            if (docsNode != null && docsNode.isArray()) {
                for (JsonNode bookNode : docsNode) {
                    String authorAlternativeName = bookNode.has("author_alternative_name") ?
                            bookNode.get("author_alternative_name").toString() : null;
                    String authorName = bookNode.has("author_name") ?
                            bookNode.get("author_name").get(0).asText() : null;
                    int firstPublishYear = bookNode.has("first_publish_year") ?
                            bookNode.get("first_publish_year").asInt() : -1;
                    String title = bookNode.has("title") ?
                            bookNode.get("title").asText() : "Unknown Title";
                    String subject = bookNode.has("subject") ?
                            bookNode.get("subject").toString() : "No Subject";
                    String leadingISBN = null;
                    if (bookNode.has("isbn") && bookNode.get("isbn").isArray() && !bookNode.get("isbn").isEmpty()) {
                        leadingISBN = bookNode.get("isbn").get(0).asText();
                    }
                    String firstSentence = bookNode.has("first_sentence") ?
                            bookNode.get("first_sentence").toString() : null;

                    books.add(new Book(authorAlternativeName, authorName, firstPublishYear, title, subject, leadingISBN, firstSentence));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
