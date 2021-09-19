package com.example.flixster.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixster.R;

public class DetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String overView = intent.getStringExtra("OverView");
        String backDropUrl = intent.getStringExtra("backDropUrl");
        String posterUrl = intent.getStringExtra("posterUrl");
        float ratings = intent.getFloatExtra("ratings", 0.0f);

        TextView movieTitle = findViewById(R.id.movieTitle);
        TextView movieOverview = findViewById(R.id.movieOverview);
        TextView rating = findViewById(R.id.movieRating);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ImageView backDrop = findViewById(R.id.movieBackdrop);
        ImageView poster = findViewById(R.id.moviePoster);

        movieTitle.setText(title);
        movieOverview.setText(overView);
        rating.setText(String.format("%.1f", ratings));
        ratingBar.setRating(ratings);
        Glide.with(this)
                .load(backDropUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(backDrop);
        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(poster);

    }
}
