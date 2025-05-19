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
                    // Extracting author_name from the new array format
                    String authorName = bookNode.has("author_name") ?
                            bookNode.get("author_name").get(0).asText() : null;

                    // Extracting other fields as before
                    int firstPublishYear = bookNode.has("first_publish_year") ?
                            bookNode.get("first_publish_year").asInt() : -1;
                    String title = bookNode.has("title") ?
                            bookNode.get("title").asText() : "Unknown Title";

                    // Extracting ISBNs from the "isbn" array (or handling any other available ISBN keys)
                    String leadingISBN = null;
                    if (bookNode.has("isbn") && bookNode.get("isbn").isArray() && !bookNode.get("isbn").isEmpty()) {
                        leadingISBN = bookNode.get("isbn").get(0).asText();
                    }

                    // Extracting book cover URL using the "cover_i" field (which is a cover ID)
                    String bookCoverUrl = generateBookCoverUrlFromCoverId(bookNode);

                    // Adding the parsed book data to the list
                    books.add(new Book(null, authorName, firstPublishYear, title, null, leadingISBN, null, bookCoverUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private static String generateBookCoverUrlFromCoverId(JsonNode bookNode) {
        if (bookNode.has("cover_i")) {
            int coverId = bookNode.get("cover_i").asInt();
            return "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
        }
        return null;
    }
}
