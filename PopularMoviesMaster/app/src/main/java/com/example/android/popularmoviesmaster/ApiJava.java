package com.example.android.popularmoviesmaster;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Callback;
/**
 * Created by DELL on 5/8/2016.
 */
public interface ApiJava {
    @GET("movie/popular?api_key="+BuildConfig.MOBDB_API_KEY)
    Call<Movie.MovieResult> getPopularMovies();
    //void getPopularMovies(Callback<Movie.MovieResult> cb);
    @GET("movie/top_rated?api_key="+BuildConfig.
            MOBDB_API_KEY)
    Call<Movie.MovieResult> getRatedMovies();
    //void getRatedMovies(Callback<Movie.MovieResult> cb);

    @GET ("movie/{id}/videos?api_key="+ BuildConfig.MOBDB_API_KEY)
    Call<Trailers> getTrailers(@Path("id") int id);

    @GET ("movie/{id}/reviews?api_key="+BuildConfig.MOBDB_API_KEY)
    Call<Reviews> getReview(
            @Path("id") int id
    );
}
