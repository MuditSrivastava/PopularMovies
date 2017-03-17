package com.example.android.popularmoviesmaster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


import static com.example.android.popularmoviesmaster.MainActivity.m2;
import static com.example.android.popularmoviesmaster.MainActivity.mAdapter;

/**
 * Created by DELL on 3/17/2017.
 */

public class FavActivity extends AppCompatActivity  {
    RecyclerView mRecyclerView;
    static int noGrid=2;
    public static int c=0;
    public static FragmentManager fm;
    Toolbar toolbar;
    Movie movie;
    public static MoviesAdapter favAdapter;
    public MovieDetailFragment f;

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
        toolbar.setTitle("Favourites");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView myTitle = (TextView) toolbar.getChildAt(0);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Satisfy-Regular.ttf");
        myTitle.setTypeface(tf,Typeface.BOLD);
        favAdapter = new MoviesAdapter(this);
        favAdapter.setMovieList(m2);
        favAdapter.notifyDataSetChanged();
       /* if (getIntent().hasExtra(FAV_PIC)) {
            favAdapter = getIntent().getParcelableExtra(FAV_PIC);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a Hit parcelable");
        }*/
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
       // ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(favAdapter);
        //scaleAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(favAdapter);


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

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



}

