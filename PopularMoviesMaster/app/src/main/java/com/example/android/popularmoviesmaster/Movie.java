package com.example.android.popularmoviesmaster;


import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;


@SimpleSQLTable(table = "movieTable", provider = "MovieProvider")
public class Movie implements Parcelable {
   
   @SimpleSQLColumn("col_rating")
   @SerializedName("vote_average")
    private String rating;

   
    @SimpleSQLColumn("col_title")
    @SerializedName("title")
    private String title;

   
    @SimpleSQLColumn("col_poster")
    @SerializedName("poster_path")
    private String poster;

   
    @SimpleSQLColumn("col_rdate")
    @SerializedName("release_date")
    private String rdate;

   
    @SimpleSQLColumn("col_overview")
    @SerializedName("overview")
    private String overview;

   
   @SimpleSQLColumn("col_id")
   @SerializedName("id")
    private  int id;

   
    @SimpleSQLColumn("col_backdrop")
    @SerializedName("backdrop_path")
    private String backdrop;


    public Movie() {}

    protected Movie(Parcel in) {
        rating =in.readString();
        title = in.readString();
        poster = in.readString();
        rdate =in.readString();
        overview = in.readString();
        id=in.readInt();
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
        return (rating ) ;
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

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

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
        parcel.writeInt(id);
        parcel.writeString(backdrop);
    }


    public static class MovieResult {
        private List<Movie> results;

        MovieResult(List<Movie> l){results = l;}

        public List<Movie> getResults() {
            return results;
        }
    }
}