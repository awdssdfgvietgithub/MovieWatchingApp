package com.example.chitietphim.Model;

public class MovieItem {

    private String maphim;
    private String title;
    private String description;
    private String imageUrl;
    public MovieItem(String maphim, String title, String description, String imageUrl) {
        this.maphim = maphim;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
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
}

