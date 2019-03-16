package com.example.movieapp.data.source.remote;

import com.example.movieapp.data.models.Movie;
import com.example.movieapp.data.models.Review;
import com.example.movieapp.data.models.Video;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApiService {
    @POST("/movie/{movie_id}/rating")
    Single<Void> rateMovie(@Path("movieId") Long movieId,
                           @Body Object object,
                           @Query("api_key") String apiKey);

    @GET("/movie/{sortBy}")
    Single<List<Movie>> getMovies(@Path("sortBy") String sortBy,
                                  @Query("api_key") String apiKey,
                                  @Query("page") int page);

    @GET("/movie/{movieId}")
    Single<Movie> getMoviesDetails(@Path("movieId") Long movieId,
                                   @Query("api_key") String apiKey);

    @GET("/movie/{movieId}/videos")
    Single<List<Video>> getMoviesVideos(@Path("movieId") Long movieId,
                                        @Query("api_key") String apiKey);

    @GET("/movie/{movieId}/reviews")
    Single<List<Review>> getMoviesReviews(@Path("movieId") Long movieId,
                                          @Query("api_key") String apiKey,
                                          @Query("page") int page);
}
