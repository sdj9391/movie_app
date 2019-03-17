package com.example.movieapp.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp.data.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("SELECT * FROM favoriteMovies WHERE id = :movieId")
    LiveData<Movie> getFavoriteMovie(Long movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(Movie movie);

    @Delete
    int deleteFavouriteMovie(Movie movie);
}