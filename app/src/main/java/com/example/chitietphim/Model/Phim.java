package com.example.chitietphim.Model;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private String nguonPhim;
    private String noiDungPhim;
    private String soDiemPhim;
    private String namRaMat;
    private String anhBia;
    private String nguonTrailer;
    private String maTheLoai;

    public Phim() {
    }

    public Phim(String maPhim, String tenPhim, String nguonPhim, String noiDungPhim, String soDiemPhim, String namRaMat, String anhBia, String nguonTrailer, String maTheLoai) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.nguonPhim = nguonPhim;
        this.noiDungPhim = noiDungPhim;
        this.soDiemPhim = soDiemPhim;
        this.namRaMat = namRaMat;
        this.anhBia = anhBia;
        this.nguonTrailer = nguonTrailer;
        this.maTheLoai = maTheLoai;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getNguonPhim() {
        return nguonPhim;
    }

    public void setNguonPhim(String nguonPhim) {
        this.nguonPhim = nguonPhim;
    }

    public String getNoiDungPhim() {
        return noiDungPhim;
    }

    public void setNoiDungPhim(String noiDungPhim) {
        this.noiDungPhim = noiDungPhim;
    }

    public String getSoDiemPhim() {
        return soDiemPhim;
    }

    public void setSoDiemPhim(String soDiemPhim) {
        this.soDiemPhim = soDiemPhim;
    }

    public String getNamRaMat() {
        return namRaMat;
    }

    public void setNamRaMat(String namRaMat) {
        this.namRaMat = namRaMat;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

    public String getNguonTrailer() {
        return nguonTrailer;
    }

    public void setNguonTrailer(String nguonTrailer) {
        this.nguonTrailer = nguonTrailer;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }
}
