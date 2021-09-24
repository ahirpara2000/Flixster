package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.R;
import com.example.flixster.models.DetailPage;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class MovieAdaper extends  RecyclerView.Adapter<MovieAdaper.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdaper(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        TextView tvRating;
        RatingBar rating;
        RelativeLayout container;
        TextView overlay;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
            rating = itemView.findViewById(R.id.rating);
            container = itemView.findViewById(R.id.container);
            overlay = itemView.findViewById(R.id.overlay);
        }

        public void bind(Movie movie) {

            float votes = movie.getVote_average();

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            tvRating.setText(String.format("%.1f", movie.getVote_average()));
            rating.setRating(votes);
            String imageUrl;

            if(votes  >= 3) {
                overlay.setVisibility(View.VISIBLE);
            }

            // if phone is in landscape
            // then imageURL = back drop image
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getBackdropPath();
            }
            // else imageUrl = poster image
            else
            {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(ivPoster);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailPage.class);
                    intent.putExtra("movie", Parcels.wrap(movie));

                    Pair<View, String> p1 = Pair.create((View)tvTitle, "title");
                    Pair<View, String> p2 = Pair.create((View)tvOverview, "overview");
                    Pair<View, String> p3 = Pair.create((View)rating, "rating");
                    Pair<View, String> p4 = Pair.create((View)ivPoster, "poster");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)context, p1, p2, p3, p4);

                    context.startActivity(intent, options.toBundle());
                }
            });
        }

    }
}
