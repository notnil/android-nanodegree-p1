package com.loganspears.movies;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loganspears.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_MOVIE_JSON = "movie_json";

    /**
     * The dummy content this fragment is presenting.
     */
    private Movie movie;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE_JSON)) {
            String movieJson = getArguments().getString(ARG_MOVIE_JSON);
            movie = new Gson().fromJson(movieJson, Movie.class);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(movie.getTitle());
            }
        }
    }

    private static final DateFormat releaseDateFormat = new SimpleDateFormat("mm/dd/yyyy", Locale.US);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        TextView releaseDateTextView = (TextView) rootView.findViewById(R.id.textView);
        TextView voteAvgTextView = (TextView) rootView.findViewById(R.id.textView2);
        TextView overviewTextView = (TextView) rootView.findViewById(R.id.textView3);
        ImageView posterImageView = (ImageView) rootView.findViewById(R.id.imageView);

        releaseDateTextView.setText("Released: " + releaseDateFormat.format(movie.getReleaseDate()));
        voteAvgTextView.setText("Vote Average: " + movie.getVoteAverage());
        overviewTextView.setText(movie.getOverview());
        Picasso.with(getActivity()).load(movie.getPosterPath()).into(posterImageView);
        return rootView;
    }
}
