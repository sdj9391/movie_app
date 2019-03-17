package com.example.movieapp.views.fragments;

import com.example.movieapp.utils.Constants;

public class TopRatedMoviesFragment extends PopularMoviesFragment {
    @Override
    protected String getMoviesType() {
        return Constants.SORT_TOP_RATED;
    }
}
