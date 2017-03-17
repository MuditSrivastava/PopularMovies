package com.example.android.popularmoviesmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.example.android.popularmoviesmaster.FavActivity.favAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {
    public static final String EXTRA_MOVIE = "movie";
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
    private Movie mMovie;
    RecyclerView rvTrailer;

    public MovieDetailFragment() {

    }
    public void getMov(Movie mMovie){

        this.mMovie=mMovie;
        if(mMovie!=null){

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
                       // listTr.size();
                        trailersAdapter.swapList(listTr);
                        if (!listTr.isEmpty()){
                            trailersText.setVisibility(View.VISIBLE);
                            rvTrailer.setVisibility(View.VISIBLE);
                            trailerCardView.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast toast = null;
                        if (response.code() == 401) {
                           toast = Toast.makeText(getActivity(), "Unauthenticated", Toast.LENGTH_SHORT);
                        } else if (response.code() >= 400) {
                            toast = Toast.makeText(getActivity(), "Client Error " + response.code()
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
                            toast = Toast.makeText(getActivity(), "Unauthenticated", Toast.LENGTH_SHORT);
                        } else if (response.code() >= 400) {
                           toast = Toast.makeText(getActivity(), "Client Error " + response.code()
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


            String ratText=mMovie.getRating()+"/10";
            rating.setText(ratText);
            if(mMovie.getRating()!=null){
            float d = Float.parseFloat(mMovie.getRating());
            rb.setRating((Math.round(d) / 2));}
            rdate.setText(mMovie.getRdate());
            title.setText(mMovie.getTitle());
            description.setText(mMovie.getOverview());
            Picasso.with(getActivity())
                    .load(mMovie.getPoster())
                    .into(poster);
            Picasso.with(getActivity())
                    .load(mMovie.getBackdrop())
                    .into(backdrop);
            if (sharedpreferences.contains(String.valueOf(mMovie.getId()))) {
                f.setImageResource(R.drawable.ic_favorite_white_24dp);
            } else
                f.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
               sharedpreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.activity_movie_detail, container, false);
       ;

        if (getActivity().getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getActivity().getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
//            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        backdrop = (ImageView) view.findViewById(R.id.backdrop);
        rating = (TextView) view.findViewById(R.id.movie_rating);
        rdate = (TextView) view.findViewById(R.id.movie_rdate);
        title = (TextView) view.findViewById(R.id.movie_title);
        description = (TextView) view.findViewById(R.id.movie_overview);
        poster = (ImageView) view.findViewById(R.id.movie_poster);
        rb=(RatingBar) view.findViewById(R.id.ratingBar1);
        trailerCardView=(CardView) view.findViewById(R.id.trailerCardView);
        trailersText=(TextView)view.findViewById(R.id.trailersText);
        f=(FloatingActionButton)view.findViewById(R.id.fab);

        trailersAdapter = new TrailersAdapter(listTr, getActivity());
        rvTrailer = (RecyclerView) view.findViewById(R.id.trailerRv);
        rvTrailer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(trailersAdapter);
        List<Trailers.SingleTrailer> items=new ArrayList<>();
        items.add(new Trailers.SingleTrailer());
        trailersAdapter.swapList(items);
        rvTrailer.setVisibility(View.GONE);

        reviewCard=(CardView)view.findViewById(R.id.reviewCardView);
        reviewCard.setVisibility(View.GONE);
        reviewAdapter = new ReviewAdapter(listRv, getActivity());
        RecyclerView rvReview = (RecyclerView) view.findViewById(R.id.reviewRv);
        rvReview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvReview.setAdapter(reviewAdapter);

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!sharedpreferences.contains(String.valueOf(mMovie.getId()))) {
                        Snackbar.make(view, getResources().getText(R.string.add_fav), Snackbar.LENGTH_SHORT).show();
                        editor.putInt(String.valueOf(mMovie.getId()), mMovie.getId());
                        editor.apply();
                        getActivity().getContentResolver().insert(MovieTableTable.CONTENT_URI,MovieTableTable.getContentValues(mMovie,false));
                        f.setImageResource(R.drawable.ic_favorite_white_24dp);
                            favAdapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(view, getResources().getText(R.string.rem_fav), Snackbar.LENGTH_SHORT).show();
                        int result = getActivity().getContentResolver().delete(MovieTableTable.CONTENT_URI,MovieTableTable.FIELD_COL_ID + "=?", new String[]{String.valueOf(mMovie.getId())});
                        Log.e("Result", String.valueOf(mMovie.getId()));
                        editor.remove(String.valueOf(mMovie.getId()));
                        editor.apply();
                        f.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        favAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    return  view;
    }
}
