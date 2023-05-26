package com.example.chitietphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chitietphim.Model.ClassLS;
import com.example.chitietphim.R;

import java.util.ArrayList;

public class AdapterLS extends ArrayAdapter {
    private Context context;
    private int layout;
    private ArrayList<ClassLS> arrayList;


    public AdapterLS(@NonNull Context context, int layout, ArrayList<ClassLS> arrayList) {
        super(context, layout, arrayList);
        this.context=context;
        this.layout=layout;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public ArrayList<ClassLS> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ClassLS> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ClassLS ifs = arrayList.get(i);
        if(view == null){
            view = LayoutInflater.from(context).inflate(layout,null);
        }

        TextView txtngayGioXem = (TextView)view.findViewById(R.id.ngayGioXem);
        txtngayGioXem.setText(ifs.getNgayGioXem());
        TextView txtmaKH = (TextView)view.findViewById(R.id.maKhachHang);
        txtmaKH.setText(ifs.getMaKhachHang());
        TextView txtmaPH = (TextView)view.findViewById(R.id.maPhim);
        txtmaPH.setText(ifs.getMaPhim());
        TextView txtDiem = (TextView) view.findViewById(R.id.soDiemDanhGia);
        txtDiem.setText(ifs.getDiem());
        return view;
    }
}
