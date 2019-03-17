package com.example.movieapp.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movieapp.R;
import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.Review;
import com.example.movieapp.viewmodels.MovieViewModel;
import com.example.movieapp.views.adapters.ReviewListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MovieReviewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;
    private MovieViewModel viewModel;
    private LinearLayoutManager layoutManager;

    private ReviewListAdapter reviewListAdapter;

    private boolean isLoading = false;
    private int page = 0;
    private int totalPages = 1;
    private Movie movie;

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        emptyTextView = findViewById(R.id.text_view_empty);

        Bundle data = getIntent().getExtras();
        if (data == null) {
            Log.e("Error", "Data found null");
            finish();
            return;
        }

        movie = data.getParcelable(MovieDetailsActivity.EXTRA_MOVIE);
        if (movie == null) {
            Log.e("Error", "Movie found null");
            finish();
            return;
        }
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        swipeRefreshLayout.setOnRefreshListener(onSwipeToRefresh);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        loadMoreItems();
        viewModel.getPageNumber().observe(this, this::setPageNumber);
        viewModel.getTotalPages().observe(this, this::setTotalPages);
        viewModel.getLoading().observe(this, this::showLoading);
        viewModel.getMoviesReviewsError().observe(this, this::showError);
    }

    protected void loadMoreItems() {
        if (!isLoading) {
            isLoading = true;
            viewModel.getMoviesReviews(movie.getId(), page).observe(this, this::showData);
        }
    }

    void showError(Boolean isError) {
        if (!isError) {
            emptyTextView.setVisibility(View.GONE);
        } else {
            String errorMsg = getString(R.string.msg_item_loading_error);
            if (reviewListAdapter != null && reviewListAdapter.getItemCount() != 0) {
                emptyTextView.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(emptyTextView, errorMsg, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText(errorMsg);
            }
        }
    }

    private void showLoading(Boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    private void setPageNumber(Integer page) {
        this.page = page;
    }

    void showData(List<Review> reviewList) {
        if (reviewList == null || reviewList.size() == 0) {
            showError(true);
            return;
        }

        if (reviewListAdapter == null) {
            reviewListAdapter = new ReviewListAdapter(reviewList);
            recyclerView.setAdapter(reviewListAdapter);
        } else {
            reviewListAdapter.setAll(reviewList);
        }
    }
}
