package com.example.mybookapp.searchtab;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookapp.R;
import com.example.mybookapp.parsing.Book;
import com.example.mybookapp.parsing.CoverParser;
import com.example.mybookapp.saves.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books = new ArrayList<>();
    private int expandedPosition = -1; // Tracks the expanded item position
    private BookRepository bookRepository;

    public BookAdapter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        // Set basic book details
        holder.textViewTitle.setText(book.getTitle() != null ? book.getTitle() : "Title: N/A");
        holder.textViewAuthor.setText(book.getAuthorName() != null ? "Author: " + book.getAuthorName() : "Author: N/A");
        holder.textViewFirstPublishYear.setText(book.getFirstPublishYear() != 0 ? "Year: " + book.getFirstPublishYear() : "Year: N/A");

        // Set expanded content
        holder.textViewSubject.setText(book.getSubject() != null ? "Subject: " + book.getSubject() : "Subject: N/A");
        holder.textViewFirstSentence.setText(book.getFirstSentence() != null ? "First Sentence: " + book.getFirstSentence() : "First Sentence: N/A");
        holder.textViewISBN.setText(book.getIsbn() != null ? "ISBN: " + book.getIsbn() : "ISBN: N/A");

        // Handle expand/collapse logic
        boolean isExpanded = position == expandedPosition;
        holder.expandedLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Get the book cover URL
        String coverUrl = book.getBookCoverUrl();

        // Use CoverParser to load the image
        if (coverUrl != null) {
            Log.d("Cover Check", "Cover URL: " + coverUrl); // Log URL to check
            CoverParser.loadBookCover(coverUrl, holder.imageViewBookCover);
        } else {
            holder.imageViewBookCover.setImageResource(R.drawable.placeholder); // Fallback if no cover URL
        }

        // Handle the Save button logic
        holder.buttonSaveBook.setOnClickListener(v -> {
            bookRepository.saveBook(book);
        });

        holder.itemView.setOnClickListener(v -> {
            int previousExpandedPosition = expandedPosition;
            expandedPosition = isExpanded ? -1 : position;
            notifyItemChanged(previousExpandedPosition);
            notifyItemChanged(expandedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    // ViewHolder class
    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAuthor, textViewFirstPublishYear;
        TextView textViewSubject, textViewFirstSentence, textViewISBN;
        ImageView imageViewBookCover;
        ImageButton buttonSaveBook;  // Changed to ImageButton if it's an ImageButton
        LinearLayout expandedLayout;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookCover = itemView.findViewById(R.id.imageViewBookCover);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewFirstPublishYear = itemView.findViewById(R.id.textViewFirstPublishYear);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
            textViewFirstSentence = itemView.findViewById(R.id.textViewFirstSentence);
            textViewISBN = itemView.findViewById(R.id.textViewISBN);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            buttonSaveBook = itemView.findViewById(R.id.buttonSaveBook);  // Make sure this matches your layout (ImageButton or Button)
        }
    }
}
