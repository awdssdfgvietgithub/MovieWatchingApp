package com.example.chitietphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitietphim.Model.IconModel;
import com.example.chitietphim.R;

import java.util.ArrayList;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private ArrayList<IconModel> iconModelsList;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public IconAdapter(ArrayList<IconModel> iconModelsList, Context context) {
        this.iconModelsList = iconModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.icons_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.icon.setImageResource(iconModelsList.get(position).getImageView());
        holder.iconName.setText(iconModelsList.get(position).getIconTitle());
    }

    @Override
    public int getItemCount() {
        return iconModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView iconName;
        ImageView icon;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.playback_icon);
            iconName = itemView.findViewById(R.id.icon_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
