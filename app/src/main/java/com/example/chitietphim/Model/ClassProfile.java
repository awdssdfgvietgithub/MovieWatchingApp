package com.example.chitietphim.Model;

public class ClassProfile {
    private String maKhachHang;
    private String tenKhachHang;
    private String SDT;
    private String email;
    private String matkhau;

    public ClassProfile() {
    }

    public ClassProfile(String maKhachHang, String tenKhachHang, String SDT, String email, String matkhau) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.SDT = SDT;
        this.email = email;
        this.matkhau = matkhau;
    }

    public String getMaKhachHang() {return maKhachHang;}

    public void setMaKhachHang(String maKhachHang) {this.maKhachHang = maKhachHang;}

    public String getTenKhachHang() { return tenKhachHang;}

    public void setTenKhachHang(String tenKhachHang) {this.tenKhachHang = tenKhachHang;}

    public String getSDT() {return SDT;}

    public void setSDT(String SDT) {this.SDT = SDT;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getMatkhau() {return matkhau;}

    public void setMatkhau(String matkhau) {this.matkhau = matkhau;}
}
