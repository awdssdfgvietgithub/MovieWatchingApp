package com.example.chitietphim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitietphim.Model.MovieItem;
import com.example.chitietphim.R;
import com.example.chitietphim.chitietphim;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<MovieItem> movieItems;

    private List<MovieItem> lMovies;

    public MovieAdapter() {

    }


    public void setData(List<MovieItem> list) {
        this.lMovies = list;
        notifyDataSetChanged();

    }

    public MovieAdapter(Context context, List<MovieItem> movieItems) {
        this.context = context;
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_movie_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem movieItem = movieItems.get(position);

        holder.titleTextView.setText(movieItem.getTitle());
        holder.descriptionTextView.setText(movieItem.getDescription() + " ★");

        Picasso.get().load(movieItem.getImageUrl()).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến ImageDetailActivity và truyền thông tin hình ảnh
                Intent intent = new Intent(context, chitietphim.class);
                intent.putExtra("MaPhim", movieItem.getMaphim());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView titleTextView;
        public TextView descriptionTextView;

        public RecyclerView rcvMovie;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            rcvMovie = itemView.findViewById(R.id.rcv_list_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Xử lý sự kiện khi mục được click
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                MovieItem movieItem = movieItems.get(position);
                // Thực hiện hành động chuyển trang hoặc xử lý thông tin hình ảnh
                // Ví dụ: Mở màn hình chi tiết hình ảnh với thông tin từ imageItem
                Intent intent = new Intent(context, chitietphim.class);
                intent.putExtra("MaPhim", movieItem.getMaphim());
                context.startActivity(intent);
            }
        }
    }
}