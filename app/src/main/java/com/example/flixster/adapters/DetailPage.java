package com.example.flixster.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class DetailPage extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("Movie_id");
        String title = intent.getStringExtra("Title");
        String overView = intent.getStringExtra("OverView");
        String backDropUrl = intent.getStringExtra("backDropUrl");
        String posterUrl = intent.getStringExtra("posterUrl");
        float ratings = intent.getFloatExtra("ratings", 0.0f);

        TextView movieTitle = findViewById(R.id.movieTitle);
        TextView movieOverview = findViewById(R.id.movieOverview);
        TextView rating = findViewById(R.id.movieRating);
        TextView genre = findViewById(R.id.genre);
        TextView runTime = findViewById(R.id.runTime);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ImageView backDrop = findViewById(R.id.movieBackdrop);
        ImageView poster = findViewById(R.id.moviePoster);


        String details_url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(details_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    List<String> genre_list = Movie.getGenreList(jsonObject.getJSONArray("genres"));
                    genre.setText(TextUtils.join(" | ", genre_list));
                    int runtime = jsonObject.getInt("runtime");
                    int run_hours = runtime / 60;
                    int run_minutes = runtime % 60;
                    runTime.setText(String.format("%dh %02dm", run_hours, run_minutes));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("Details Page", "OnFailure");
            }
        });

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
