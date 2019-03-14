package com.example.movieapp.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieapp.data.models.Movie;
import com.example.movieapp.utils.Constants;

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase INSTANCE;

    public abstract MovieDao movieDao();

    private static final Object lock = new Object();

    public static MoviesDatabase getInstance(Context context) {
        synchronized (lock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, Constants.DATABASE_NAME)
                        .build();
            }
            return INSTANCE;
        }
    }
}
