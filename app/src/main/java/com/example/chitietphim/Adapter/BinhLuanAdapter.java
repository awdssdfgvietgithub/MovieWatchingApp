package com.example.chitietphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chitietphim.Model.BinhLuan;
import com.example.chitietphim.R;

import java.util.ArrayList;

public class BinhLuanAdapter extends ArrayAdapter {
    Context context;
    ArrayList<BinhLuan> arrayList;
    int layout;
    TextView tvMaKhachHang, tvNoiDungBinhLuan;

    public BinhLuanAdapter(@NonNull Context context, int layout, ArrayList<BinhLuan> arrayList) {
        super(context, layout, arrayList);
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<BinhLuan> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<BinhLuan> arrayList) {
        this.arrayList = arrayList;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public TextView getTvMaKhachHang() {
        return tvMaKhachHang;
    }

    public void setTvMaKhachHang(TextView tvMaKhachHang) {
        this.tvMaKhachHang = tvMaKhachHang;
    }

    public TextView getTvNoiDungBinhLuan() {
        return tvNoiDungBinhLuan;
    }

    public void setTvNoiDungBinhLuan(TextView tvNoiDungBinhLuan) {
        this.tvNoiDungBinhLuan = tvNoiDungBinhLuan;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BinhLuan binhLuan = arrayList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        tvMaKhachHang = convertView.findViewById(R.id.tvMaKhachHang);
        tvMaKhachHang.setText(binhLuan.getMaKhachHang());
        tvNoiDungBinhLuan = convertView.findViewById(R.id.tvNoiDungBinhLuan);
        tvNoiDungBinhLuan.setText(binhLuan.getNoiDungBinhLuan());
        return convertView;
    }
}
