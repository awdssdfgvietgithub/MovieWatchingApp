package com.example.chitietphim.Model;

public class GridItemModel {

    String videoImage;
    String videoName;
    String videoID;

    public GridItemModel(String videoImage, String videoName, String videoID) {
        this.videoImage = videoImage;
        this.videoName = videoName;
        this.videoID = videoID;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}
