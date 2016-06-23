package com.example.android.popularmoviesmaster;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Callback;

public interface ApiJava {
    @GET("movie/popular?api_key="+BuildConfig.MOBDB_API_KEY)
    Call<Movie.MovieResult> getPopularMovies();
    
    @GET("movie/top_rated?api_key="+BuildConfig.
            MOBDB_API_KEY)
    Call<Movie.MovieResult> getRatedMovies();
    

    @GET ("movie/{id}/videos?api_key="+ BuildConfig.MOBDB_API_KEY)
    Call<Trailers> getTrailers(@Path("id") int id);

    @GET ("movie/{id}/reviews?api_key="+BuildConfig.MOBDB_API_KEY)
    Call<Reviews> getReview(
            @Path("id") int id
    );
}
