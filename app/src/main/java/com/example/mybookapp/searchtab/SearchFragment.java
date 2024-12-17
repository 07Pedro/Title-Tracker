package com.example.mybookapp.searchtab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybookapp.R;
import com.example.mybookapp.apiRequest.ApiRequest;
import com.example.mybookapp.booklist.BookListFragment;
import com.example.mybookapp.parsing.Book;

import java.util.List;

public class SearchFragment extends Fragment {

    private EditText editTextTitle, editTextAuthor, editTextISBN;
    private ApiRequest apiRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextAuthor = view.findViewById(R.id.editTextAuthor);
        editTextISBN = view.findViewById(R.id.editTextISBN);

        apiRequest = new ApiRequest();
        apiRequest.setOnBooksFetchedListener(this::navigateToBookList);

        view.findViewById(R.id.buttonSearch).setOnClickListener(v -> onSearchClicked());

        return view;
    }

    private void onSearchClicked() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String isbn = editTextISBN.getText().toString().trim();

        if (title.isEmpty() && author.isEmpty() && isbn.isEmpty()) {
            Toast.makeText(getContext(), "Please enter at least one search field", Toast.LENGTH_SHORT).show();
        } else {
            apiRequest.makeConnectionAsync(title, author, isbn);
            Toast.makeText(getContext(), "Searching...", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToBookList(List<Book> books) {
        if (books.isEmpty()) {
            Toast.makeText(getContext(), "No books found.", Toast.LENGTH_SHORT).show();
        } else {
            BookListFragment bookListFragment = BookListFragment.newInstance(books);
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragmentContainer, bookListFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
