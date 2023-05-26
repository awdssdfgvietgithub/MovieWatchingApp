package com.example.chitietphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chitietphim.Model.ClassProfile;
import com.example.chitietphim.R;

import java.util.ArrayList;

public class AdapterProfile extends ArrayAdapter {

    private Context context;
    private int layout;
    private ArrayList<ClassProfile> arrayList;


    public AdapterProfile(@NonNull Context context, int layout, ArrayList<ClassProfile> arrayList) {
        super(context, layout, arrayList);
        this.context=context;
        this.layout=layout;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public int getLayout() {return layout;}

    public void setLayout(int layout) {this.layout = layout;}

    public ArrayList<ClassProfile> getArrayList() {return arrayList;}

    public void setArrayList(ArrayList<ClassProfile> arrayList) {this.arrayList = arrayList;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ClassProfile ifs = arrayList.get(i);
        if(view == null){
            view = LayoutInflater.from(context).inflate(layout,null);
        }

        TextView txtmakh = (TextView)view.findViewById(R.id.txtmakhachhang);
        txtmakh.setText(ifs.getMaKhachHang());
        TextView txttenKH = (TextView)view.findViewById(R.id.txttenKhachHang);
        txttenKH.setText(ifs.getTenKhachHang());
        TextView txtsdt = (TextView)view.findViewById(R.id.txtsdt);
        txtsdt.setText(ifs.getSDT());
        TextView txtemail = (TextView) view.findViewById(R.id.txtemail);
        txtemail.setText(ifs.getEmail());
        TextView txtmk = (TextView) view.findViewById(R.id.txtmatkhau);
        txtmk.setText(ifs.getMatkhau());
        return view;
    }

}
