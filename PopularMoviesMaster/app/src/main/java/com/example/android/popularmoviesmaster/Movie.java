package com.example.android.popularmoviesmaster;


import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by DELL on 5/8/2016.
 */
public class Movie implements Parcelable {
    @SerializedName("vote_average")
    private String rating;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("release_date")
    private String rdate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("backdrop_path")
    private String backdrop;


    public Movie() {}

    protected Movie(Parcel in) {
        rating =in.readString();
        title = in.readString();
        poster = in.readString();
        rdate =in.readString();
        overview = in.readString();
        backdrop = in.readString();
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

    public String getRating() {
        return rating + "/10";
    }
    public void setRating(String rating) {
        this.rating = rating;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRdate() {
        return rdate;
    }
    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop() {
        return "http://image.tmdb.org/t/p/w500"  + backdrop;
    }
    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(rating);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(rdate);
        parcel.writeString(overview);
        parcel.writeString(backdrop);
    }

    public static class MovieResult {
        private List<Movie> results;
        public List<Movie> getResults() {
            return results;
        }
    }
}