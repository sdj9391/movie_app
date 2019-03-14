package com.example.movieapp.data.source.remote;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApiService {
    @POST("/movie/{movie_id}/rating")
    Observable<Response<JsonObject>> rateMovie(@Path("movieId") Long movieId,
                                               @Query("api_key") String apiKey);

    @GET("/movie/{sortBy}")
    Observable<Response<JsonObject>> getMovies(@Path("sortBy") String sortBy,
                                               @Query("api_key") String apiKey,
                                               @Query("page") int page);

    @GET("/movie/{movieId}")
    Observable<Response<JsonObject>> getMoviesDetails(@Path("movieId") Long movieId,
                                                      @Query("api_key") String apiKey);
}
