package com.example.movieapp.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.R;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

    }
}
