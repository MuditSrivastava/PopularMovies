package com.example.android.popularmoviesmaster;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";

    private Movie mMovie;
    ImageView backdrop;
    TextView rating;
    TextView rdate;
    ImageView poster;
    TextView title;
    TextView trailersText;
    TextView description;
    RatingBar rb;
    CardView trailerCardView;
    private Trailers trailers;
    private TrailersAdapter trailersAdapter;
    ApiJava tmdbApi;
    String Base_URL = "http://api.themoviedb.org/3/";
    Call<Reviews> callRv;
    private List<Trailers.SingleTrailer> listTr;
    List<Reviews.SingleReview> listRv;
    private Reviews reviews;
    private ReviewAdapter reviewAdapter;
    CardView reviewCard;
    FloatingActionButton f;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        backdrop = (ImageView) findViewById(R.id.backdrop);
        rating = (TextView) findViewById(R.id.movie_rating);
        rdate = (TextView) findViewById(R.id.movie_rdate);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_overview);
        poster = (ImageView) findViewById(R.id.movie_poster);
        rb=(RatingBar) findViewById(R.id.ratingBar1);
        trailerCardView=(CardView) findViewById(R.id.trailerCardView);
        trailersText=(TextView)findViewById(R.id.trailersText);
        f=(FloatingActionButton)findViewById(R.id.fab);

        sharedpreferences = this.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if (sharedpreferences.contains(String.valueOf(mMovie.getId()))) {
            f.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else
            f.setImageResource(R.drawable.ic_favorite_border_white_24dp);

        trailersAdapter = new TrailersAdapter(listTr, this);
        final RecyclerView rvTrailer = (RecyclerView) findViewById(R.id.trailerRv);
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(trailersAdapter);
        List<Trailers.SingleTrailer> items=new ArrayList<>();
        items.add(new Trailers.SingleTrailer());
        trailersAdapter.swapList(items);
        rvTrailer.setVisibility(View.GONE);

        reviewCard=(CardView)findViewById(R.id.reviewCardView);
        reviewCard.setVisibility(View.GONE);
        reviewAdapter = new ReviewAdapter(listRv, this);
        RecyclerView rvReview = (RecyclerView) findViewById(R.id.reviewRv);
        rvReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvReview.setAdapter(reviewAdapter);
        String ratText=mMovie.getRating()+"/10";
        rating.setText(ratText);
         float d = Float.parseFloat(mMovie.getRating());
         rb.setRating((Math.round(d) / 2));
        rdate.setText(mMovie.getRdate());
        title.setText(mMovie.getTitle());
        description.setText(mMovie.getOverview());
        Picasso.with(this)
               .load(mMovie.getPoster())
             .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);


        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!sharedpreferences.contains(String.valueOf(mMovie.getId()))) {
                        Snackbar.make(view, getResources().getText(R.string.add_fav), Snackbar.LENGTH_SHORT).show();
                        editor.putInt(String.valueOf(mMovie.getId()), mMovie.getId());
                        editor.apply();
                        getContentResolver().insert(MovieTableTable.CONTENT_URI,MovieTableTable.getContentValues(mMovie,false));
                        f.setImageResource(R.drawable.ic_favorite_white_24dp);
                        MainActivity.favAdapter.notifyDataSetChanged();

                    } else {
                        Snackbar.make(view, getResources().getText(R.string.rem_fav), Snackbar.LENGTH_SHORT).show();
                        int result = getContentResolver().delete(MovieTableTable.CONTENT_URI,MovieTableTable.FIELD_COL_ID + "=?", new String[]{String.valueOf(mMovie.getId())});
                        Log.e("Result", String.valueOf(mMovie.getId()));
                        editor.remove(String.valueOf(mMovie.getId()));
                        editor.apply();
                        f.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        MainActivity.favAdapter.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbApi = retrofit.create(ApiJava.class);

        Call<Trailers> callTr = tmdbApi.getTrailers(mMovie.getId());
        callTr.enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Response<Trailers> response) {
                try {
                    trailers = response.body();
                    listTr = trailers.getTrailers();
                    trailersAdapter.swapList(listTr);
                    if (!listTr.isEmpty()){
                        trailersText.setVisibility(View.VISIBLE);
                        rvTrailer.setVisibility(View.VISIBLE);
                        //trailerImage.setVisibility(View.VISIBLE);
                        trailerCardView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = null;
                    if (response.code() == 401) {
                        toast = Toast.makeText(MovieDetail.this, "Unauthenticated", Toast.LENGTH_SHORT);
                    } else if (response.code() >= 400) {
                        toast = Toast.makeText(MovieDetail.this, "Client Error " + response.code()
                                + " " + response.message(), Toast.LENGTH_SHORT);
                    }
                    try {
                        toast.show();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    Log.e("getQuestions threw: ", t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        callRv = tmdbApi.getReview(mMovie.getId());
        callRv.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Response<Reviews> response) {
                try {
                    reviews = response.body();
                    listRv = reviews.getReviews();
                    reviewAdapter.swapList(listRv);
                    if(!listRv.isEmpty()){
                        reviewCard.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Toast toast = null;
                    if (response.code() == 401) {
                        toast = Toast.makeText(MovieDetail.this, "Unauthenticated", Toast.LENGTH_SHORT);
                    } else if (response.code() >= 400) {
                        toast = Toast.makeText(MovieDetail.this, "Client Error " + response.code()
                                + " " + response.message(), Toast.LENGTH_SHORT);
                    }
                    try {
                        toast.show();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    Log.e("getQuestions threw: ", t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_detail_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
