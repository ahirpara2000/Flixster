package com.example.flixster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.R;
import com.example.flixster.models.Cast;
import com.example.flixster.models.Movie;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>{

    Context context;
    List<Cast> casts;

    public CastAdapter(Context context, List<Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View castView = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);
        return new CastAdapter.ViewHolder(castView);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position) {
        Cast cast = casts.get(position);
        holder.bind(cast);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivProfile;
        public TextView tvName;
        public TextView tvCharacter;

        // Constructor - accepts entire row item
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find each view by id you set up in the list_item.xml
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvCharacter = itemView.findViewById(R.id.tvCharacter);
        }

        public void bind(Cast cast) {

            Log.d("CastAdapter: ", "name" + cast.getName());

            Glide.with(context)
                    .load(cast.getProfile_path())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(ivProfile);
            tvName.setText(cast.getName());
            tvCharacter.setText(cast.getCharacter());
        }
    }
}
