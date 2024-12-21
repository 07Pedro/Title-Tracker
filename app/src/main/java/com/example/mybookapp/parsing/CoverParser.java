package com.example.mybookapp.parsing;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mybookapp.R;

public class CoverParser {

    public static void loadBookCover(String coverUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(coverUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView);

        Glide.with(imageView.getContext())
                .load(coverUrl)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource.getIntrinsicWidth() == 1 && resource.getIntrinsicHeight() == 1) {
                            imageView.setImageResource(R.drawable.placeholder);
                            Log.d("Cover Check", "1x1 Image detected, showing default image.");
                        } else {
                            imageView.setImageDrawable(resource);
                            Log.d("Cover Check", "Valid image loaded.");
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        imageView.setImageResource(R.drawable.placeholder);
                    }
                });
    }

    public static String getBookCoverUrl(String isbn) {
        if (isbn != null && !isbn.isEmpty()) {
            return "https://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg";
        }
        return null;
    }
}
