package com.example.movieapp.data.source;

import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.PagedMovies;
import com.example.movieapp.data.models.PagedReview;
import com.example.movieapp.data.models.PagedVideo;
import com.example.movieapp.data.models.Review;
import com.example.movieapp.data.models.Video;
import com.example.movieapp.data.source.remote.AppApiInstance;
import com.example.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

class MoviesRemoteRepository {

    private static final String TAG = "MoviesRemoteRepository";
    private CompositeDisposable disposable;

    MoviesRemoteRepository() {
        disposable = new CompositeDisposable();
    }

    private final MutableLiveData<List<Movie>> moviesData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> moviesLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> rateMovieSuccess = new MutableLiveData<>();
    private final MutableLiveData<Integer> pageNumber = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalPages = new MutableLiveData<>();

    private final MutableLiveData<List<Video>> moviesVideos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> moviesVideosError = new MutableLiveData<>();

    private final MutableLiveData<List<Review>> moviesReviews = new MutableLiveData<>();
    private final MutableLiveData<Boolean> moviesReviewsError = new MutableLiveData<>();

    MutableLiveData<List<Movie>> getMovies(String sortBy, int page) {
        getMoviesCall(sortBy, page);
        return moviesData;
    }

    MutableLiveData<Boolean> getMoviesLoadError() {
        return moviesLoadError;
    }

    MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    MutableLiveData<Integer> getPage() {
        return pageNumber;
    }

    MutableLiveData<Integer> getTotalPage() {
        return totalPages;
    }

    MutableLiveData<Boolean> rateMovie(Long movieId, Float rating) {
        rateMovieCall(movieId, rating);
        return rateMovieSuccess;
    }

    MutableLiveData<List<Video>> getMoviesVideos(Long movieId) {
        getMoviesVideosCall(movieId);
        return moviesVideos;
    }

    MutableLiveData<Boolean> getMoviesVideosError() {
        return moviesVideosError;
    }

    MutableLiveData<List<Review>> getMoviesReviews(Long movieId, int page) {
        getMoviesReviewsCall(movieId, page);
        return moviesReviews;
    }

    MutableLiveData<Boolean> getMoviesReviewsError() {
        return moviesReviewsError;
    }

    private void getMoviesCall(String sortBy, int page) {
        loading.setValue(true);
        disposable.add(AppApiInstance.getApi().getMovies(sortBy, Constants.MOVIE_API_KEY, page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagedMovies>() {
                    @Override
                    public void onSuccess(PagedMovies pagedMovies) {
                        loading.setValue(false);
                        int pageNum = pagedMovies.getPage();
                        List<Movie> currentMovieList = moviesData.getValue();
                        List<Movie> newMovieList = pagedMovies.getMovieList();

                        if (currentMovieList == null || pageNum == 1) {
                            currentMovieList = new ArrayList<>();
                        }
                        currentMovieList.addAll(newMovieList);
                        moviesData.setValue(currentMovieList);
                        pageNumber.setValue(pagedMovies.getPage());
                        totalPages.setValue(pagedMovies.getTotalPages());
                        moviesLoadError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        moviesLoadError.setValue(true);
                    }
                }));
    }

    private void rateMovieCall(Long movieId, Float rating) {
        Map<String, Float> jsonValue = new HashMap<>();
        jsonValue.put("value", rating);

        disposable.add(AppApiInstance.getApi().rateMovie(movieId, jsonValue, Constants.MOVIE_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        rateMovieSuccess.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rateMovieSuccess.setValue(false);
                    }
                }));
    }

    private void getMoviesVideosCall(Long movieId) {
        loading.setValue(true);
        disposable.add(AppApiInstance.getApi().getMoviesVideos(movieId, Constants.MOVIE_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagedVideo>() {
                    @Override
                    public void onSuccess(PagedVideo pagedVideo) {
                        loading.setValue(false);
                        moviesVideos.setValue(pagedVideo.getVideoList());
                        pageNumber.setValue(pagedVideo.getPage());
                        totalPages.setValue(pagedVideo.getTotalPages());
                        moviesVideosError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        moviesVideosError.setValue(true);
                    }
                }));
    }

    private void getMoviesReviewsCall(Long movieId, int page) {
        loading.setValue(true);
        disposable.add(AppApiInstance.getApi().getMoviesReviews(movieId, Constants.MOVIE_API_KEY, page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagedReview>() {
                    @Override
                    public void onSuccess(PagedReview pagedReview) {
                        loading.setValue(false);
                        int pageNum = pagedReview.getPage();
                        List<Review> currentReviewList = moviesReviews.getValue();
                        List<Review> newReviewList = pagedReview.getReviewList();

                        if (currentReviewList == null || pageNum == 1) {
                            currentReviewList = new ArrayList<>();
                        }
                        currentReviewList.addAll(newReviewList);
                        moviesReviews.setValue(currentReviewList);
                        pageNumber.setValue(pagedReview.getPage());
                        totalPages.setValue(pagedReview.getTotalPages());
                        moviesReviewsError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        moviesReviewsError.setValue(true);
                    }
                }));
    }

    void clearApiTasks() {
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
