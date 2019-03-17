package com.example.movieapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.Review;
import com.example.movieapp.data.models.Video;
import com.example.movieapp.data.source.MoviesRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MoviesRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MoviesRepository(application);
    }

    public MutableLiveData<List<Movie>> getMovies(String sortBy, int page) {
        return repository.getMovies(sortBy, page);
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return repository.getFavoriteMovies();
    }

    public LiveData<Movie> getFavoriteMovie(Long movieId) {
        return repository.getFavoriteMovie(movieId);
    }

    public void insertAsFavorite(Movie movie) {
        repository.insertAsFavorite(movie);
    }

    public void deleteFromFavorite(Movie movie) {
        repository.deleteFromFavorite(movie);
    }

    public MutableLiveData<Boolean> getMoviesError() {
        return repository.getMoviesError();
    }

    public MutableLiveData<List<Video>> getMoviesVideos(Long movieId) {
        return repository.getMoviesVideos(movieId);
    }

    public MutableLiveData<Boolean> getMoviesVideosError() {
        return repository.getMoviesVideosError();
    }

    public MutableLiveData<List<Review>> getMoviesReviews(Long movieId, int page) {
        return repository.getMoviesReviews(movieId, page);
    }

    public MutableLiveData<Boolean> getMoviesReviewsError() {
        return repository.getMoviesReviewsError();
    }

    public MutableLiveData<Boolean> getLoading() {
        return repository.getLoading();
    }

    public MutableLiveData<Integer> getPageNumber() {
        return repository.getPageNumber();
    }

    public MutableLiveData<Integer> getTotalPages() {
        return repository.getTotalPages();
    }

    public MutableLiveData<Boolean> rateMovies(Long MovieId, Float rating) {
        return repository.rateMovies(MovieId, rating);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.clearApiTasks();
    }
}
