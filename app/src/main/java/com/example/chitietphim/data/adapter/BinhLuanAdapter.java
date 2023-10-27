package com.example.chitietphim.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chitietphim.R;
import com.example.chitietphim.data.model.BinhLuan;

import java.util.ArrayList;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BinhLuan> data;

    public BinhLuanAdapter(Context context, ArrayList<BinhLuan> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public BinhLuanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_binhluan, parent, false);
        return new BinhLuanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BinhLuanAdapter.ViewHolder holder, int position) {
        BinhLuan bl = data.get(position);

        holder.tvName.setText(bl.getTenKhachHang());
        holder.tvBody.setText(bl.getNoiDungBinhLuan());
        holder.tvDate.setText(bl.getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvBody, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvBody = itemView.findViewById(R.id.tvNoiDungBinhLuan);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
