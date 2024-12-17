package com.example.mybookapp.searchtab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookapp.R;
import com.example.mybookapp.parsing.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books = new ArrayList<>();

    // Update the dataset
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

        holder.textViewTitle.setText(book.getTitle() != null ? book.getTitle() : "N/A");
        holder.textViewAuthor.setText(book.getAuthorName() != null ? book.getAuthorName() : "N/A");
        holder.textViewFirstPublishYear.setText(String.valueOf(book.getFirstPublishYear()));
        holder.textViewISBN.setText(book.getIsbn() != null ? book.getIsbn() : "N/A");


        holder.imageViewBookCover.setImageResource(R.drawable.placeholder);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    // ViewHolder class
    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewAuthor, textViewFirstPublishYear, textViewISBN;
        ImageView imageViewBookCover;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookCover = itemView.findViewById(R.id.imageViewBookCover);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewFirstPublishYear = itemView.findViewById(R.id.textViewFirstPublishYear);
            textViewISBN = itemView.findViewById(R.id.textViewISBN);
        }
    }
}
