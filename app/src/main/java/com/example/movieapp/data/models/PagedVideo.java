package com.example.movieapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedVideo extends Paged {
    @SerializedName("results")
    private List<Video> videoList;

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
