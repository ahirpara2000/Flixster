package com.example.flixster.models;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.R;

import org.parceler.Parcels;

public class VideoActivity extends AppCompatActivity {

    ImageView backDrop;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        String backdrop = intent.getStringExtra("poster");

        backDrop = findViewById(R.id.backDrop);

        Glide.with(this)
                .load(String.format("https://image.tmdb.org/t/p/w342/%s", backdrop))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(backDrop);
    }
}
