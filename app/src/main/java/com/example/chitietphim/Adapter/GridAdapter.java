package com.example.chitietphim.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitietphim.Model.GridItemModel;
import com.example.chitietphim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<GridItemModel> arrayList;

    public GridAdapter(@NonNull Context context, int resource, ArrayList<GridItemModel> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GridItemModel itemModel = arrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }
        TextView videoName = convertView.findViewById(R.id.grid_videoName);
        videoName.setText(itemModel.getVideoName());

        ImageView videoImg = convertView.findViewById(R.id.grid_videoImage);
        Picasso.get().load(itemModel.getVideoImage()).into(videoImg);

        return convertView;
    }

}
