package com.example.mybookapp.saves;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mybookapp.parsing.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void saveBook(Book book);

    @Query("SELECT * FROM book")
    List<Book> getAllBooks();
}
