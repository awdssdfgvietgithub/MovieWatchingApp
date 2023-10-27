package com.example.chitietphim.data.model;

public class BinhLuan {
    private String tenKhachHang;
    private String noiDungBinhLuan;
    private String date;

    public BinhLuan() {
    }

    public BinhLuan(String tenKhachHang, String noiDungBinhLuan, String date) {
        this.tenKhachHang = tenKhachHang;
        this.noiDungBinhLuan = noiDungBinhLuan;
        this.date = date;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNoiDungBinhLuan() {
        return noiDungBinhLuan;
    }

    public void setNoiDungBinhLuan(String noiDungBinhLuan) {
        this.noiDungBinhLuan = noiDungBinhLuan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
