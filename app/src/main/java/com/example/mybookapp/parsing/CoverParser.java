package com.example.mybookapp.parsing;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mybookapp.R;

public class CoverParser {

    public static void loadBookCover(String coverUrl, ImageView imageView) {
        if (TextUtils.isEmpty(coverUrl)) {
            Log.d("CoverParser", "Empty URL, setting placeholder.");
            imageView.setImageResource(R.drawable.placeholder);
            return;
        }

        Log.d("CoverParser", "Loading cover from: " + coverUrl);

        Glide.with(imageView.getContext())
                .load(coverUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageView);
    }
}
