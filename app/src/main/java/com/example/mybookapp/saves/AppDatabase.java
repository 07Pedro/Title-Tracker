package com.example.mybookapp.saves;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mybookapp.parsing.Book;

@Database(entities = {Book.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

}
