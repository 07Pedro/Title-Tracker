package com.example.mybookapp.saves;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new SavedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedBookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.textViewTitle.setText(book.getTitle() != null ? book.getTitle() : "Title: N/A");
        holder.textViewAuthor.setText(book.getAuthorName() != null ? "Author: " + book.getAuthorName() : "Author: N/A");
        holder.textViewFirstPublishYear.setText(book.getFirstPublishYear() != 0 ? "Year: " + book.getFirstPublishYear() : "Year: N/A");

        String isbn = book.getIsbn();
        String coverUrl = CoverParser.getBookCoverUrl(isbn);

        if (coverUrl != null) {
            CoverParser.loadBookCover(coverUrl, holder.imageViewBookCover);
        } else {
            holder.imageViewBookCover.setImageResource(R.drawable.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class SavedBookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAuthor, textViewFirstPublishYear;
        ImageView imageViewBookCover;

        public SavedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookCover = itemView.findViewById(R.id.imageViewBookCover);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewFirstPublishYear = itemView.findViewById(R.id.textViewFirstPublishYear);
        }
    }
}
