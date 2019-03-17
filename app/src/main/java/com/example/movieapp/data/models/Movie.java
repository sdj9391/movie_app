package com.example.movieapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favoriteMovies")
public class Movie implements Parcelable {

    @PrimaryKey
    private long id;

    @SerializedName("release_date")
    private String releaseDate;
    private String title;
    @SerializedName("original_title")
    private String orignalTitle;
    private String tagline;
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String coverPath;
    @SerializedName("vote_count")
    private long voteCount;
    @SerializedName("vote_average")
    private float voteAverage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrignalTitle() {
        return orignalTitle;
    }

    public void setOrignalTitle(String orignalTitle) {
        this.orignalTitle = orignalTitle;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(long id, String releaseDate, String title, String orignalTitle, String tagline,
                 String overview, String posterPath, String coverPath, long voteCount, float voteAverage) {
        this.id = id;
        this.releaseDate = releaseDate;
        this.title = title;
        this.orignalTitle = orignalTitle;
        this.tagline = tagline;
        this.overview = overview;
        this.posterPath = posterPath;
        this.coverPath = coverPath;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

    protected Movie(Parcel in) {
        id = in.readLong();
        releaseDate = in.readString();
        title = in.readString();
        orignalTitle = in.readString();
        tagline = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        coverPath = in.readString();
        voteCount = in.readLong();
        voteAverage = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(releaseDate);
        parcel.writeString(title);
        parcel.writeString(orignalTitle);
        parcel.writeString(tagline);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
        parcel.writeString(coverPath);
        parcel.writeLong(voteCount);
        parcel.writeFloat(voteAverage);
    }
}
