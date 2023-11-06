package com.example.chitietphim.data.model;

public class MovieItem {

    private String maphim;
    private String title;
    private String description;
    private String imageUrl;
    private String maTheLoai;

    public MovieItem(String maphim, String title, String description, String imageUrl, String maTheLoai) {
        this.maphim = maphim;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.maTheLoai = maTheLoai;
    }


    public String getMaphim() {
        return maphim;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

}

