package com.example.movieapp.data.source;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.Review;
import com.example.movieapp.data.models.Video;
import com.example.movieapp.data.source.local.MovieDao;
import com.example.movieapp.data.source.local.MoviesDatabase;

import java.util.List;

public class MoviesRepository {
    private MovieDao movieDao;
    private MoviesRemoteRepository moviesRemoteRepository;

    public MoviesRepository(Application application) {
        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(application);
        movieDao = moviesDatabase.movieDao();
        moviesRemoteRepository = new MoviesRemoteRepository();
    }

    public void clearApiTasks() {
        moviesRemoteRepository.clearApiTasks();
    }

    public MutableLiveData<List<Movie>> getMovies(String sortBy, int page) {
        return moviesRemoteRepository.getMovies(sortBy, page);
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return movieDao.getAllFavoriteMovies();
    }

    public MutableLiveData<Boolean> getMoviesError() {
        return moviesRemoteRepository.getMoviesLoadError();
    }

    public MutableLiveData<List<Video>> getMoviesVideos(Long movieId) {
        return moviesRemoteRepository.getMoviesVideos(movieId);
    }

    public MutableLiveData<Boolean> getMoviesVideosError() {
        return moviesRemoteRepository.getMoviesVideosError();
    }

    public MutableLiveData<List<Review>> getMoviesReviews(Long movieId, int page) {
        return moviesRemoteRepository.getMoviesReviews(movieId, page);
    }

    public MutableLiveData<Boolean> getMoviesReviewsError() {
        return moviesRemoteRepository.getMoviesReviewsError();
    }

    public MutableLiveData<Boolean> getLoading() {
        return moviesRemoteRepository.getLoading();
    }

    public MutableLiveData<Boolean> rateMovies(Long MovieId, Float rating) {
        return moviesRemoteRepository.rateMovie(MovieId, rating);
    }

    public void insertAsFavorite(Movie movie) {
        new InsertMovieTask(movieDao).execute(movie);
    }

    public void deleteFromFavorite(Movie movie) {
        new DeleteMovieTask(movieDao).execute(movie);
    }

    public Movie getMoviefromFavorite(Long movieId) {
        return movieDao.getFavoriteMovie(movieId);
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        MovieDao movieDao;

        public InsertMovieTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insertFavoriteMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        MovieDao movieDao;

        public DeleteMovieTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteFavouriteMovie(movies[0]);
            return null;
        }
    }
}
