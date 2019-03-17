package com.example.movieapp.data.source.remote;

import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.PagedMovies;
import com.example.movieapp.data.models.PagedReview;
import com.example.movieapp.data.models.PagedVideo;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApiService {

    String API_VERSION = "/3";

    @POST(API_VERSION + "/movie/{movie_id}/rating")
    Single<Void> rateMovie(@Path("movieId") Long movieId,
                           @Body Object object,
                           @Query("api_key") String apiKey);

    @GET(API_VERSION + "/movie/{sortBy}")
    Single<PagedMovies> getMovies(@Path("sortBy") String sortBy,
                                  @Query("api_key") String apiKey,
                                  @Query("page") int page);

    @GET(API_VERSION + "/movie/{movieId}")
    Single<Movie> getMoviesDetails(@Path("movieId") Long movieId,
                                   @Query("api_key") String apiKey);

    @GET(API_VERSION + "/movie/{movieId}/videos")
    Single<PagedVideo> getMoviesVideos(@Path("movieId") Long movieId,
                                       @Query("api_key") String apiKey);

    @GET(API_VERSION + "/movie/{movieId}/reviews")
    Single<PagedReview> getMoviesReviews(@Path("movieId") Long movieId,
                                         @Query("api_key") String apiKey,
                                         @Query("page") int page);
}
