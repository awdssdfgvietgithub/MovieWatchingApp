package com.example.chitietphim.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitietphim.R;
import com.example.chitietphim.data.model.MovieItem;
import com.example.chitietphim.ui.activities.ChiTietPhimActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultsSearchAdapter extends RecyclerView.Adapter<ResultsSearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MovieItem> data;

    public ResultsSearchAdapter(Context context, ArrayList<MovieItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ResultsSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phim_item_view, parent, false);
        return new ResultsSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsSearchAdapter.ViewHolder holder, int position) {
        MovieItem movieItem = data.get(position);

        holder.textViewMovieName.setText(movieItem.getTitle());
        holder.textViewMovieRating.setText(movieItem.getDescription() + " ★");
        Picasso.get().load(movieItem.getImageUrl()).into(holder.imageViewMovie);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageViewMovie;
        public TextView textViewMovieName;
        public TextView textViewMovieRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.image_view_movie);
            textViewMovieName = itemView.findViewById(R.id.text_view_movie_name);
            textViewMovieRating = itemView.findViewById(R.id.text_view_movie_rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                MovieItem movieItem = data.get(position);
                Intent intent = new Intent(context, ChiTietPhimActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("MaPhim", movieItem.getMaphim());
                context.startActivity(intent);
            }
        }
    }
}
