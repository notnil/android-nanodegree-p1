package com.loganspears.movies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loganspears.movies.model.Movie;
import com.loganspears.movies.model.MovieDetail;
import com.loganspears.movies.model.Review;
import com.loganspears.movies.networking.ReviewResponse;
import com.loganspears.movies.networking.TheMovieDbClient;
import com.loganspears.movies.networking.TheMovieDbService;
import com.loganspears.movies.model.Video;
import com.loganspears.movies.networking.VideoResponse;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {

    public static final String ARG_MOVIE_JSON = "movie_json";

    private Movie movie;
    private List<Review> reviewList;
    private List<Video> videoList;
    private ListView videoListView;
    private ListView reviewListView;

    public MovieDetailFragment() {}

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

        videoListView = (ListView) rootView.findViewById(R.id.listView1);
        videoListView.setOnItemClickListener(getOnItemClickListener(videoList));
        reviewListView = (ListView) rootView.findViewById(R.id.listView2);
        reviewListView.setOnItemClickListener(getOnItemClickListener(reviewList));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshVideos();
        refreshReviews();
    }

    private void refreshReviews() {
        final ProgressDialog mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage(getString(R.string.movie_detail_loading));
        mDialog.setCancelable(false);
        mDialog.show();

        TheMovieDbService service = TheMovieDbClient.getClient(getActivity()).create(TheMovieDbService.class);

        String movieId = Integer.toString(movie.getId());
        Call<ReviewResponse> reviewResponseCall = service.getReviews(movieId);
        reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = response.body();
                reviewList = reviewResponse.getResults();
                reviewListView.setAdapter(new MovieDetailAdapter(getActivity(), reviewList));
                reviewListView.setOnItemClickListener(getOnItemClickListener(reviewList));
                mDialog.hide();
            }
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.movie_details_no_connection, Toast.LENGTH_SHORT).show();
                Log.d("DETAIL", t.getMessage());
                mDialog.hide();
            }
        });
    }

    private void refreshVideos() {
        final ProgressDialog mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage(getString(R.string.movie_detail_loading));
        mDialog.setCancelable(false);
        mDialog.show();

        TheMovieDbService service = TheMovieDbClient.getClient(getActivity()).create(TheMovieDbService.class);

        String movieId = Integer.toString(movie.getId());
        Call<VideoResponse> videoResponseCall = service.getTrailers(movieId);
        videoResponseCall.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                VideoResponse videoResponse = response.body();
                videoList = videoResponse.getVideoList();
                videoListView.setAdapter(new MovieDetailAdapter(getActivity(), videoList));
                videoListView.setOnItemClickListener(getOnItemClickListener(videoList));
                mDialog.hide();
            }
            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.movie_details_no_connection, Toast.LENGTH_SHORT).show();
                Log.d("DETAIL", t.getMessage());
                mDialog.hide();
            }
        });
    }

    private AdapterView.OnItemClickListener getOnItemClickListener(final List list) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieDetail movieDetail = (MovieDetail) list.get(position);
                Intent intent = movieDetail.getIntent();
                if (intent != null){
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.move_detail_unsupported_video_type, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private static class MovieDetailAdapter extends ArrayAdapter  {

        public MovieDetailAdapter(Context context, List objects) {
            super(context, android.R.layout.simple_list_item_2, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                row = inflater.inflate(android.R.layout.simple_list_item_2, null);
            } else {
                row = convertView;
            }
            MovieDetail movieDetail = (MovieDetail) getItem(position);
            TextView primaryContentTextView = (TextView) row.findViewById(android.R.id.text1);
            TextView secondaryContentTextView = (TextView) row.findViewById(android.R.id.text2);
            primaryContentTextView.setText(movieDetail.getPrimaryContent());
            secondaryContentTextView.setText(movieDetail.getSecondaryContent());
            return row;
        }


    }
}
