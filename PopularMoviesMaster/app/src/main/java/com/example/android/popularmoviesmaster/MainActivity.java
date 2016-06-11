package com.example.android.popularmoviesmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.framed.FrameReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
//import retrofit.RequestInterceptor;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;



public class MainActivity extends AppCompatActivity implements communicator {
    RecyclerView mRecyclerView;
    static int noGrid=2;
    public static int c=0;
    public  static MoviesAdapter mAdapter,favAdapter;
    boolean b;
    public Movie.MovieResult m;
    public static FragmentManager fm;
    public static List<Movie> movies = new ArrayList<>();
    int currstate=1;
    List<Movie> favMovieAsList;
    Toolbar toolbar;
    getMov GETMOV;
    Movie movie;
    public static int count=0;
    public static MovieDetailFragment f;
    public static communicator comm;
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isNetworkAvailable()){
            {Toast toast = Toast.makeText(this,"Internet Connection required to run this app properly", Toast.LENGTH_LONG);
                toast.show();}
        }
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView myTitle = (TextView) toolbar.getChildAt(0);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Satisfy-Regular.ttf");
        myTitle.setTypeface(tf,Typeface.BOLD);
        comm = (communicator)this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        mRecyclerView.setHasFixedSize(true);
        f = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fm=getSupportFragmentManager();

        if(f==null){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            noGrid=3;
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            noGrid=2;
        }}
        else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                noGrid=2;
            }
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                noGrid=1;
            }

        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, noGrid));


        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);





        for (int i = 0; i < 25; i++) {
            movies.add(new Movie());
        }
        m= new Movie.MovieResult(movies);


        mAdapter.setMovieList(m);
        count++;
         GETMOV=new getMov(mAdapter,this,b);
        GETMOV.progressDialog.show();
        GETMOV.execute();


    }
    @Override
    public void onStart()
    {
        super.onStart();
        GETMOV=new getMov(mAdapter,this,b);
        GETMOV.execute();
        GETMOV.progressDialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (currstate == 3) {
            favMovieAsList = MovieTableTable.getRows(this.getContentResolver().query(MovieTableTable.CONTENT_URI, null, null, null, null), true);
            favAdapter = new MoviesAdapter(this);
            Movie.MovieResult m2= new Movie.MovieResult(favMovieAsList);
            favAdapter.setMovieList(m2);
        } else {
//            rv.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        if (id == R.id.action_fav) {


                favMovieAsList =MovieTableTable.getRows(this.getContentResolver().query(MovieTableTable.CONTENT_URI, null, null, null, null), true);
                favAdapter = new MoviesAdapter(this);
                Movie.MovieResult m2 = new Movie.MovieResult(favMovieAsList);
                favAdapter.setMovieList(m2);
                  favAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(favAdapter);

                currstate = 3;
                toolbar.setTitle("Favourites");

        }

        return super.onOptionsItemSelected(item);
    }
   // fm.getSupportFragmentManager().beginTransaction().replace(R.id.fragment2, f).commit();
    @Override
    public void respond(Movie movie) {
        this.movie = movie;
        if (f != null && f.isVisible()) {
            f.getMov(movie);
        }
    }






}