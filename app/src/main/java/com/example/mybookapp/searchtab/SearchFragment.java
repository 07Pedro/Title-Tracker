package com.example.mybookapp.searchtab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mybookapp.R;

public class SearchFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextAuthor;
    private EditText editTextISBN;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextAuthor = view.findViewById(R.id.editTextAuthor);
        editTextISBN = view.findViewById(R.id.editTextISBN);

        view.findViewById(R.id.buttonSearch).setOnClickListener(v -> onSearchClicked());

        return view;
    }

    public void onSearchClicked() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String isbn = editTextISBN.getText().toString().trim();

        if (title.isEmpty() && author.isEmpty() && isbn.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in at least one search field", Toast.LENGTH_SHORT).show();
        } else {
            String message = "Searching for:\n";
            if (!title.isEmpty()) message += "Title: " + title + "\n";
            if (!author.isEmpty()) message += "Author: " + author + "\n";
            if (!isbn.isEmpty()) message += "ISBN: " + isbn;
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();


        }
    }
}
