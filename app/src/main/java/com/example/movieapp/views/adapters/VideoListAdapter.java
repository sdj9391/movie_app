package com.example.movieapp.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.models.Video;
import com.example.movieapp.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<Video> videoList;
    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VideoListAdapter(List<Video> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videoList.get(position);
        String url = Constants.YOUTUBE_IMAGE_BASE_URL + video.getKey() + Constants.YOUTUBE_IMAGE_TYPE;
        Picasso.get().load(url).into(holder.imageView);
        holder.itemView.setTag(video);
        holder.itemView.setOnClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        private ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
