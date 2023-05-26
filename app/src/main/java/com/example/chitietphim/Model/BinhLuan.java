package com.example.chitietphim.Model;

import androidx.dynamicanimation.animation.SpringAnimation;

public class BinhLuan {
    private String maKhachHang;
    private String noiDungBinhLuan;

    public BinhLuan() {
    }

    public BinhLuan(String maKhachHang, String noiDungBinhLuan) {
        this.maKhachHang = maKhachHang;
        this.noiDungBinhLuan = noiDungBinhLuan;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getNoiDungBinhLuan() {
        return noiDungBinhLuan;
    }

    public void setNoiDungBinhLuan(String noiDungBinhLuan) {
        this.noiDungBinhLuan = noiDungBinhLuan;
    }
}
