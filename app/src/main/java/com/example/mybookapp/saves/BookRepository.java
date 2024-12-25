package com.example.mybookapp.saves;

import android.content.Context;

import androidx.room.Room;

import com.example.mybookapp.saves.AppDatabase;
import com.example.mybookapp.parsing.Book;

import java.util.List;

public class BookRepository {
    private final AppDatabase database;

    public BookRepository(Context context) {
        database = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "book-database")
                .build();
    }

    public void saveBook(Book book) {
        new Thread(() -> database.bookDao().saveBook(book)).start();
    }

    public List<Book> getAllBooks() {
        return database.bookDao().getAllBooks();
    }
}
