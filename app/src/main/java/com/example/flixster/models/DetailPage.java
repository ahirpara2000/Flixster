package com.example.flixster.models;

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
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class DetailPage extends AppCompatActivity {

    TextView movieTitle;
    TextView movieOverview;
    TextView rating;
    TextView genre;
    TextView runTime;
    RatingBar ratingBar;
    ImageView backDrop;
    ImageView poster;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        Intent intent = getIntent();
        Movie movie = Parcels.unwrap(intent.getParcelableExtra("movie"));
        String movieId = intent.getStringExtra("Movie_id");

        movieTitle = findViewById(R.id.movieTitle);
        movieOverview = findViewById(R.id.movieOverview);
        rating = findViewById(R.id.movieRating);
        genre = findViewById(R.id.genre);
        runTime = findViewById(R.id.runTime);
        ratingBar = findViewById(R.id.ratingBar);
        backDrop = findViewById(R.id.movieBackdrop);
        poster = findViewById(R.id.moviePoster);


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

        movieTitle.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        rating.setText(String.format("%.1f", movie.getVote_average()));
        ratingBar.setRating(movie.getVote_average());
        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(backDrop);
        Glide.with(this)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(poster);

    }
}
