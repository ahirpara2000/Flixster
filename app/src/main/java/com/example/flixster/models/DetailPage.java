package com.example.flixster.models;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.R;

import org.json.JSONArray;
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
    ImageView thumbnail;
    ImageView poster;
    ImageView playView;
    String youtubeKey;

    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String DETAILS_URL = "https://api.themoviedb.org/3/movie/%s?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/maxresdefault.jpg";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        Intent intent = getIntent();
        Movie movie = Parcels.unwrap(intent.getParcelableExtra("movie"));
        String movieId = movie.getMovie_id();

        movieTitle = findViewById(R.id.movieTitle);
        movieOverview = findViewById(R.id.movieOverview);
        rating = findViewById(R.id.movieRating);
        genre = findViewById(R.id.genre);
        runTime = findViewById(R.id.runTime);
        ratingBar = findViewById(R.id.ratingBar);
        thumbnail = findViewById(R.id.youtubeThumbnail);
        poster = findViewById(R.id.moviePoster);
        playView = findViewById(R.id.playView);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(String.format(DETAILS_URL, movieId), new JsonHttpResponseHandler() {
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

        client.get(String.format(VIDEO_URL, movieId), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {

                    JSONArray results = json.jsonObject.getJSONArray("results");
                    youtubeKey = results.getJSONObject(0).getString("key");
                    initializeYoutubeBackDrop(youtubeKey);

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
                .load(movie.getPosterPath())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(poster);

        playView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPage.this, VideoActivity.class);
                intent.putExtra("movieId", movie.getMovie_id());
                intent.putExtra("youtubeKey", youtubeKey);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(DetailPage.this, thumbnail, "backdrop");
                startActivity(intent, options.toBundle());
            }
        });

    }

    private void initializeYoutubeBackDrop(String youtubeKey) {
        Glide.with(this)
                .load(String.format(YOUTUBE_THUMBNAIL_URL, youtubeKey))
                .into(thumbnail);
    }
}
