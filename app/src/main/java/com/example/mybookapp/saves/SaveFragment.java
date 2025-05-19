package com.example.mybookapp.saves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookapp.R;
import com.example.mybookapp.parsing.Book;

import java.util.List;

public class SaveFragment extends Fragment {

    private RecyclerView recyclerViewSavedBooks;
    private SavedBookAdapter savedBookAdapter;
    private BookRepository bookRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save, container, false);

        recyclerViewSavedBooks = view.findViewById(R.id.recyclerViewSavedBooks);
        recyclerViewSavedBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the adapter with the repository
        savedBookAdapter = new SavedBookAdapter(bookRepository);
        recyclerViewSavedBooks.setAdapter(savedBookAdapter);

        bookRepository = new BookRepository(getContext());

        fetchSavedBooks();

        return view;
    }

    void fetchSavedBooks() {
        new Thread(() -> {
            List<Book> savedBooks = bookRepository.getAllBooks();
            getActivity().runOnUiThread(() -> {
                savedBookAdapter.setBooks(savedBooks);
            });
        }).start();
    }
}
