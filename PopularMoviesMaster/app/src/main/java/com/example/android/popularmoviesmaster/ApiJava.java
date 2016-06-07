package com.example.android.popularmoviesmaster;

import retrofit.http.GET;
import retrofit.Callback;
/**
 * Created by DELL on 5/8/2016.
 */
public interface ApiJava {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
    @GET("/movie/top_rated")
    void getRatedMovies(Callback<Movie.MovieResult> cb);
}
