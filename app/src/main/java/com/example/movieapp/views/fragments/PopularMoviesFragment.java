package com.example.movieapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movieapp.R;
import com.example.movieapp.data.models.Movie;
import com.example.movieapp.utils.Constants;
import com.example.movieapp.viewmodels.MovieViewModel;
import com.example.movieapp.views.activities.MovieDetailsActivity;
import com.example.movieapp.views.adapters.MovieListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PopularMoviesFragment extends Fragment {
    private ProgressBar progressBar;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;
    MovieViewModel viewModel;
    private GridLayoutManager layoutManager;

    private MovieListAdapter movieListAdapter;

    private boolean isLoading = false;
    private int page = 0;
    private int totalPages = 1;

    RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

            if (!isLoading && (page < totalPages) && visibleItemCount < totalItemCount
                    && lastVisibleItemPosition == totalItemCount - 1 && lastVisibleItemPosition > 0) {
                loadMoreItems();
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = () -> {
        swipeRefreshLayout.setRefreshing(false);
        page = 0;
        loadMoreItems();
    };

    private View.OnClickListener onItemClickListener = view -> {
        Object object = view.getTag();
        if (object instanceof Movie) {
            Movie movie = (Movie) object;
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);
            startActivity(intent);
        } else {
            Log.e("Error", "Wrong Instance found");
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_n_empty_view, null, true);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);
        emptyTextView = view.findViewById(R.id.text_view_empty);
        return view;
    }

    protected String getMoviesType() {
        return Constants.SORT_POPULAR;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        swipeRefreshLayout.setOnRefreshListener(onSwipeToRefresh);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        loadMoreItems();
        viewModel.getPageNumber().observe(this, this::setPageNumber);
        viewModel.getTotalPages().observe(this, this::setTotalPages);
        viewModel.getLoading().observe(this, this::showLoading);
        viewModel.getMoviesError().observe(this, this::showError);
    }

    protected void loadMoreItems() {
        if (!isLoading) {
            isLoading = true;
            viewModel.getMovies(getMoviesType(), page).observe(this, this::showData);
        }
    }

    void showError(Boolean isError) {
        if (!isAdded()) {
            return;
        }

        if (!isError) {
            emptyTextView.setVisibility(View.GONE);
        } else {
            String errorMsg = getString(R.string.msg_item_loading_error);
            if (movieListAdapter != null && movieListAdapter.getItemCount() != 0) {
                emptyTextView.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(errorMsg);
            }
        }
    }

    private void showLoading(Boolean isLoading) {
        if (!isAdded()) {
            return;
        }

        this.isLoading = isLoading;
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setTotalPages(Integer totalPages) {
        if (!isAdded()) {
            return;
        }

        this.totalPages = totalPages;
    }

    private void setPageNumber(Integer page) {
        if (!isAdded()) {
            return;
        }

        this.page = page;
    }

    void showData(List<Movie> movies) {
        if (!isAdded()) {
            return;
        }

        if (movies == null || movies.size() == 0) {
            showError(true);
            return;
        }

        if (movieListAdapter == null) {
            movieListAdapter = new MovieListAdapter(movies);
            movieListAdapter.setOnItemClickListener(onItemClickListener);
            recyclerView.setAdapter(movieListAdapter);
        } else {
            movieListAdapter.setAll(movies);
        }
    }
}
