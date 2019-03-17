package com.example.movieapp.views.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.Video;
import com.example.movieapp.utils.AppUtils;
import com.example.movieapp.utils.Constants;
import com.example.movieapp.viewmodels.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    private ImageView imageView, addFavouriteButton;
    private RatingBar ratingBar;
    private TextView ratingTextView, titleTextView, dateTextView, descriptionTextView;
    private View showReviewButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Movie movie;

    private MovieViewModel viewModel;

    private View.OnClickListener onFavouriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Object object = view.getTag();
            boolean isFavourite = false;
            if (object instanceof Boolean) {
                isFavourite = (boolean) object;
            }

            if (isFavourite) {
                viewModel.deleteFromFavorite(movie);
            } else {
                viewModel.insertAsFavorite(movie);
            }

        }
    };

    private View.OnClickListener onReviewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        imageView = findViewById(R.id.image_view);
        addFavouriteButton = findViewById(R.id.button_favourite);
        ratingBar = findViewById(R.id.rating_bar);
        ratingTextView = findViewById(R.id.text_view_rating);
        titleTextView = findViewById(R.id.text_view_title);
        dateTextView = findViewById(R.id.text_view_date);
        descriptionTextView = findViewById(R.id.text_view_info);
        showReviewButton = findViewById(R.id.button_review);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        Bundle data = getIntent().getExtras();
        if (data == null) {
            Log.e("Error", "Data found null");
            finish();
            return;
        }

        movie = data.getParcelable(EXTRA_MOVIE);
        if (movie == null) {
            Log.e("Error", "Movie found null");
            finish();
            return;
        }
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getLoading().observe(this, this::showLoading);
        viewModel.getMoviesVideos(movie.getId()).observe(this, this::showData);
        viewModel.getFavoriteMovie(movie.getId()).observe(this, this::favouriteMovie);
        addFavouriteButton.setOnClickListener(onFavouriteClickListener);
        showReviewButton.setOnClickListener(onReviewClickListener);
        showMovieData();
    }

    private void favouriteMovie(Movie movie) {
        if (movie == null) {
            addFavouriteButton.setTag(false);
            addFavouriteButton.setImageResource(R.drawable.ic_heart);
        } else {
            addFavouriteButton.setTag(true);
            addFavouriteButton.setImageResource(R.drawable.ic_heart_selected);
        }
    }

    private void showMovieData() {
        String url = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_w780 + movie.getPosterPath();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading_fail)
                .into(imageView);
        float rating = movie.getVoteAverage();
        ratingTextView.setText(getString(R.string.text_rating, rating));
        if (rating != 0) {
            rating = rating / 2;
        }
        ratingBar.setRating(rating);
        titleTextView.setText(movie.getTitle());
        Calendar calendar = AppUtils.getDate(movie.getReleaseDate());
        dateTextView.setText(getString(R.string.text_date, calendar.get(Calendar.DATE),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
        descriptionTextView.setText(movie.getOverview());
    }

    private void showData(List<Video> videos) {

    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
