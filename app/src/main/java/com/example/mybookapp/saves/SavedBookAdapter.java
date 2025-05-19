package com.example.mybookapp.saves;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookapp.R;
import com.example.mybookapp.parsing.Book;
import com.example.mybookapp.parsing.CoverParser;

import java.util.ArrayList;
import java.util.List;

public class SavedBookAdapter extends RecyclerView.Adapter<SavedBookAdapter.SavedBookViewHolder> {

    private List<Book> books = new ArrayList<>();
    private final BookRepository bookRepository;

    // Constructor that takes a BookRepository to allow deletion of books
    public SavedBookAdapter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_book, parent, false);
        return new SavedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedBookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.textViewBookTitle.setText(book.getTitle() != null ? book.getTitle() : "Title: N/A");
        holder.textViewBookAuthor.setText(book.getAuthorName() != null ? "Author: " + book.getAuthorName() : "Author: N/A");

        String coverUrl = book.getBookCoverUrl();  // This should now return the correct URL
        Log.d("SavedBookAdapter", "Cover URL for book \"" + book.getTitle() + "\": " + coverUrl);

        // Load the cover image if the URL is valid
        if (coverUrl != null) {
            CoverParser.loadBookCover(coverUrl, holder.imageViewBookCover);
        } else {
            holder.imageViewBookCover.setImageResource(R.drawable.placeholder); // Use a placeholder if no cover
        }

        holder.buttonDelete.setOnClickListener(v -> deleteBook(book));
    }




    @Override
    public int getItemCount() {
        return books.size();
    }

    private void deleteBook(Book book) {
        bookRepository.deleteBookById(book.getId()); // Delete book from the database
        fetchSavedBooks();  // Refresh the list after deletion
    }

    private void fetchSavedBooks() {
        new Thread(() -> {
            List<Book> savedBooks = bookRepository.getAllBooks();
            // Update the UI on the main thread
            savedBooks.forEach(book -> {
                if (!books.contains(book)) {
                    books.add(book);
                }
            });
            notifyDataSetChanged();
        }).start();
    }

    static class SavedBookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBookTitle, textViewBookAuthor;
        ImageView imageViewBookCover;
        Button buttonDelete;

        public SavedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookCover = itemView.findViewById(R.id.imageViewBookCover);
            textViewBookTitle = itemView.findViewById(R.id.textViewBookTitle);
            textViewBookAuthor = itemView.findViewById(R.id.textViewBookAuthor);
            buttonDelete = itemView.findViewById(R.id.buttonDelete); // Initialize the Delete button
        }
    }
}
