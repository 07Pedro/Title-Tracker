package com.example.mybookapp.parsing;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(tableName = "book")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    @JsonProperty("book_Cover")
    private String bookCoverUrl;


    public Book(String authorAlternativeName, String authorName, int firstPublishYear,
                String title, String subject, String isbn, String firstSentence, String bookCoverUrl) {
        this.authorAlternativeName = authorAlternativeName;
        this.authorName = authorName;
        this.firstPublishYear = firstPublishYear;
        this.title = title;
        this.subject = subject;
        this.isbn = isbn;
        this.firstSentence = firstSentence;
        this.bookCoverUrl = bookCoverUrl;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorAlternativeName() {
        return authorAlternativeName;
    }

    public void setAuthorAlternativeName(String authorAlternativeName) {
        this.authorAlternativeName = authorAlternativeName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getFirstPublishYear() {
        return firstPublishYear;
    }

    public void setFirstPublishYear(int firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(String firstSentence) {
        this.firstSentence = firstSentence;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public void setBookCoverUrl(String bookCoverUrl) {
        this.bookCoverUrl = bookCoverUrl;
    }

    @Override
    public String toString() {
        return "Book: " + title + "\n" +
                "Author: " + authorName + "\n" +
                "Genre: " + subject + "\n" +
                "First Published: " + firstPublishYear + "\n" +
                "ISBN: " + isbn + "\n" +
                "First Sentence: " + firstSentence + "\n" +
                "BookCoverUrl: " + bookCoverUrl + '\'';
    }
}
