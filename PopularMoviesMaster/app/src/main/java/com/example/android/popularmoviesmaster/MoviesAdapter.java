package com.example.android.popularmoviesmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>
{
    private List<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MoviesAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
    }
    public boolean isPopular(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(context.getString(R.string.pref_sort_key),
                context.getString(R.string.pref_sort_popular))
                .equals(context.getString(R.string.pref_sort_popular));
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.movie_item, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);

         view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetail.class);
                intent.putExtra(MovieDetail.EXTRA_MOVIE, mMovieList.get(position));
                if(MainActivity.f!=null)
                {
                    Movie mu = mMovieList.get(position);
                    MainActivity.comm.respond(mu);
                }
                else
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        Movie movie = mMovieList.get(position);

        Picasso.with(mContext)
                .load(movie.getPoster())
                .resize(200,300)
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);

       holder.textView.setText(movie.getTitle());

    }

    @Override
    public int getItemCount()
    {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(Movie.MovieResult movieList)
    {
        this.mMovieList.clear();
        if(movieList.getResults()!=null)
         this.mMovieList.addAll(movieList.getResults());
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    }


