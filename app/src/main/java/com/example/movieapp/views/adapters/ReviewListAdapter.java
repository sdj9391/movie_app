package com.example.movieapp.views.adapters;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.models.Review;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private List<Review> reviewList;

    public ReviewListAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void setAll(List<Review> reviewList) {
        if (reviewList != null) {
            this.reviewList = reviewList;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        Context context = holder.itemView.getContext();
        holder.authorTextView.setText(context.getString(R.string.title_author, review.getAuthor()));
        holder.reviewTextView.setText(review.getContent());
        holder.urlTextView.setText(review.getUrl());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView authorTextView, reviewTextView, urlTextView;

        private ViewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.text_view_author);
            reviewTextView = itemView.findViewById(R.id.text_view_review);
            urlTextView = itemView.findViewById(R.id.text_view_url);
            urlTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
