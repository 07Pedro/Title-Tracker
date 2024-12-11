package com.example.mybookapp.parsing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    @JsonProperty("author_alternative_name")
    private String authorAlternativeName;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("first_publish_year")
    private int firstPublishYear;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("first_sentence")
    private String firstSentence;

    // Constructor
    public Book(String authorAlternativeName, String authorName, int firstPublishYear,
                String title, String subject, String isbn, String firstSentence) {
        this.authorAlternativeName = authorAlternativeName;
        this.authorName = authorName;
        this.firstPublishYear = firstPublishYear;
        this.title = title;
        this.subject = subject;
        this.isbn = isbn;
        this.firstSentence = firstSentence;
    }

    // Getters and setters
    public String getAuthorAlternativeName() {
        return authorAlternativeName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getFirstPublishYear() {
        return firstPublishYear;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    @Override
    public String toString() {
        return "Book: " + title + "\n" +
                "Author: " + authorName + "\n" +
                "Genre: " + subject + "\n" +
                "First Published: " + firstPublishYear + "\n" +
                "ISBN: " + isbn + "\n" +
                "First Sentence: " + firstSentence;
    }
}
