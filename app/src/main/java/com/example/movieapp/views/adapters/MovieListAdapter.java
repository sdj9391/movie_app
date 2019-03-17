package com.example.movieapp.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.models.Movie;
import com.example.movieapp.utils.AppUtils;
import com.example.movieapp.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<Movie> movieList;
    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MovieListAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setAll(List<Movie> movies) {
        if (movieList != null) {
            movieList = movies;
            notifyDataSetChanged();
        }
    }

    public void clearList() {
        if (movieList != null) {
            movieList.clear();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        String url = Constants.IMAGE_BASE_URL + Constants.IMAGE_SIZE_W500 + movie.getPosterPath();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loading_fail)
                .into(holder.imageView);
        Calendar calendar = AppUtils.getDate(movie.getReleaseDate());
        holder.dateTextView.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        holder.ratingTextView.setText(String.valueOf(movie.getVoteAverage()));
        holder.itemView.setTag(movie);
        holder.itemView.setOnClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ratingTextView, dateTextView;
        private ImageView imageView;

        private ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            ratingTextView = itemView.findViewById(R.id.text_view_rating);
            dateTextView = itemView.findViewById(R.id.text_view_date);
        }
    }
}
