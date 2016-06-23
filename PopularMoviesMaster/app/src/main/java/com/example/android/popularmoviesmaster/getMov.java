package com.example.android.popularmoviesmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by DELL on 6/27/2016.
 */
public class getMov extends AsyncTask<Void, Void, Movie.MovieResult> {

    private MoviesAdapter mAdapter;
    private Movie.MovieResult mR;
    private Context context;
    public ProgressDialog progressDialog;
    Boolean b;

    public getMov(MoviesAdapter mAdapter, Context context, Boolean b) {
        this.mAdapter = mAdapter;
        this.context = context;
        this.b = b;
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Just a sec ...");
    }

    @Override
    protected void onPostExecute(Movie.MovieResult mR) {
        progressDialog.dismiss();
    }

    @Override
    protected Movie.MovieResult doInBackground(Void... params) {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiJava service = restAdapter.create(ApiJava.class);


        boolean is_popular;
        is_popular = mAdapter.isPopular(context);
        if (is_popular) {
            b = true;
            Call<Movie.MovieResult> call = service.getPopularMovies();
            call.enqueue(new Callback<Movie.MovieResult>() {
                @Override
                public void onResponse(retrofit2.Response<Movie.MovieResult> movieResult) {

                    MainActivity.mAdapter.setMovieList(movieResult.body());
                }

                @Override
                public void onFailure(Throwable t) {
                    if (b) {
                        Toast toast = Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT);
                        toast.show();
                        b = false;
                    }

                }
            });
        } else {
            b = true;
            Call<Movie.MovieResult> call = service.getRatedMovies();
            call.enqueue(new Callback<Movie.MovieResult>() {
                @Override
                public void onResponse(retrofit2.Response<Movie.MovieResult> response) {
                    MainActivity.mAdapter.setMovieList(response.body());
                }

                @Override
                public void onFailure(Throwable t) {
                    if (b) {
                        Toast toast = Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT);
                        toast.show();
                        b = false;
                    }

                }
            });

        }
        return mR;
    }
}
