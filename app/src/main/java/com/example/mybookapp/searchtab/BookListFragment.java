package com.example.mybookapp.searchtab;

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
import com.example.mybookapp.saves.BookAdapter;
import com.example.mybookapp.saves.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {

    private static final String ARG_BOOK_LIST = "book_list";
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;

    public static BookListFragment newInstance(List<Book> books) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK_LIST, new ArrayList<>(books));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Initialize RecyclerView
        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));

        BookRepository bookRepository = new BookRepository(requireContext());

        bookAdapter = new BookAdapter(bookRepository);
        recyclerViewBooks.setAdapter(bookAdapter);

        if (getArguments() != null) {
            List<Book> books = (List<Book>) getArguments().getSerializable(ARG_BOOK_LIST);
            if (books != null) {
                bookAdapter.setBooks(books);
            }
        }

        // Back Button functionality
        View buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
