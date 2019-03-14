package com.example.movieapp.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp.data.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    List<Movie> getAllMoview();

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    List<Movie> getAllFevoriteMovies();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    Movie getMovieById(String movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovies(List<Movie> movies);

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    void updateFavorite(String movieId, boolean isFavorite);

    @Query("DELETE FROM movies")
    int deleteAllMoview();
}
