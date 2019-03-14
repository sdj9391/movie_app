package com.example.movieapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedMovies extends Paged {
    @SerializedName("result")
    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
