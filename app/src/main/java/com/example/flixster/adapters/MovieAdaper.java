package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        TextView tvRating;
        RatingBar rating;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
            rating = itemView.findViewById(R.id.rating);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            tvRating.setText(String.format("%.1f", movie.getVote_average()));
            rating.setRating(movie.getVote_average());
            String imageUrl;

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
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Movie movie = movies.get(position);

            String title = movie.getTitle();
            String overView = movie.getOverview();
            String backDropUrl = movie.getBackdropPath();
            String posterUrl = movie.getPosterPath();
            float ratings = movie.getVote_average();

            Intent intent = new Intent(context, DetailPage.class);
            intent.putExtra("Title", title);
            intent.putExtra("OverView", overView);
            intent.putExtra("backDropUrl", backDropUrl);
            intent.putExtra("posterUrl", posterUrl);
            intent.putExtra("ratings", ratings);
            context.startActivity(intent);
        }

    }
}
