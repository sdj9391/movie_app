package com.example.movieapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedReview extends Paged {
    @SerializedName("results")
    private List<Review> reviewList;

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
