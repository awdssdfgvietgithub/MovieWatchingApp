package com.example.chitietphim.Model;

public class ClassLS {
    private String ngayGioXem;
    private String maKhachHang;
    private String maPhim;

    private String diem;

    public ClassLS() {
    }

    public ClassLS(String ngayGioXem, String maKhachHang, String maPhim, String diem) {
        this.ngayGioXem = ngayGioXem;
        this.maKhachHang = maKhachHang;
        this.maPhim = maPhim;
        this.diem = diem;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getNgayGioXem() {
        return ngayGioXem;
    }

    public void setNgayGioXem(String ngayGioXem) {
        this.ngayGioXem = ngayGioXem;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }
}