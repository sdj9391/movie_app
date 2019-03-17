package com.example.movieapp.views.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.movieapp.data.models.Movie;

import java.util.List;

public class FavouriteMoviesFragment extends PopularMoviesFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.removeOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    protected void loadMoreItems() {
        viewModel.getFavoriteMovies().observe(this, this::showData);
    }

    @Override
    void showData(List<Movie> movies) {
        if (movies == null || movies.size() == 0) {
            showError(true);
            return;
        }
        super.showData(movies);
    }
}
