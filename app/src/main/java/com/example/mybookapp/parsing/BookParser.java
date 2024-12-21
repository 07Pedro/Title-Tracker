package com.example.mybookapp.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
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
                    List<String> subjectList = new ArrayList<>();
                    if (bookNode.has("subject") && bookNode.get("subject").isArray()) {
                        bookNode.get("subject").forEach(node -> subjectList.add(node.asText()));
                    }
                    String randomSubjects = selectRandomSubjects(subjectList, 3);

                    String leadingISBN = null;
                    if (bookNode.has("isbn") && bookNode.get("isbn").isArray() && !bookNode.get("isbn").isEmpty()) {
                        leadingISBN = bookNode.get("isbn").get(0).asText();
                    }
                    String firstSentence = null;
                    if (bookNode.has("first_sentence") && bookNode.get("first_sentence").isArray()) {
                        JsonNode firstSentenceNode = bookNode.get("first_sentence").get(0);
                        if (firstSentenceNode != null) {
                            firstSentence = firstSentenceNode.asText();
                        }
                    }

                    String bookCoverUrl = generateBookCoverUrl(leadingISBN);

                    books.add(new Book(authorAlternativeName, authorName, firstPublishYear, title, randomSubjects, leadingISBN, firstSentence, bookCoverUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private static String generateBookCoverUrl(String isbn) {
        if (isbn != null && !isbn.isEmpty()) {
            return "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
        }
        return null;
    }

    private static String selectRandomSubjects(List<String> subjects, int limit) {
        if (subjects == null || subjects.isEmpty()) {
            return "No Subject";
        }
        Collections.shuffle(subjects);
        return subjects.stream()
                .limit(limit)
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("No Subject");
    }
}
