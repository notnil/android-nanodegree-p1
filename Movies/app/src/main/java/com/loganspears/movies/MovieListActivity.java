package com.loganspears.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loganspears.movies.model.Movie;
import com.loganspears.movies.model.MovieOrder;
import com.loganspears.movies.networking.MovieResponse;
import com.loganspears.movies.networking.TheMovieDbClient;
import com.loganspears.movies.networking.TheMovieDbService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MovieListActivity.class.getName(), "onCreate");

        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MovieListActivity.class.getName(), "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MovieListActivity.class.getName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MovieListActivity.class.getName(), "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MovieListActivity.class.getName(), "onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MovieListActivity.class.getName(), "onResume");

        refreshMovies();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.preferences:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                //
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshMovies() {
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.movie_list_loading));
        mDialog.setCancelable(false);
        mDialog.show();

        TheMovieDbService service = TheMovieDbClient.getClient(this).create(TheMovieDbService.class);

        Call<MovieResponse> call = service.getPopularMovies();
        if (getMovieOrder() == MovieOrder.HIGHEST_RATED){
            call = service.getTopRatedMovies();
        }
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                for (Movie movie : movieResponse.getResults()){
                    Log.d("MOVIES", movie.getTitle() + " " + Integer.toString(movie.getId()));
                }
                GridView gridView = (GridView) findViewById(R.id.movie_grid);
                gridView.setAdapter(new MovieGridAdapter(MovieListActivity.this, movieResponse.getResults()));
                mDialog.hide();
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieListActivity.this, R.string.movie_list_no_connection, Toast.LENGTH_SHORT).show();
                Log.d("MOVIES", t.getMessage());
                mDialog.hide();
            }
        });
    }

    private MovieOrder getMovieOrder() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String value = preferences.getString(SettingsActivity.KEY_PREF_SORT_ORDER, MovieOrder.MOST_POPULAR.getName());
        return MovieOrder.fromString(value);
    }

    private static class MovieGridAdapter extends BaseAdapter {
        private Context mContext;
        private final List<Movie> movies;

        public MovieGridAdapter(Context c, List<Movie> movies) {
            this.mContext = c;
            this.movies = new ArrayList<>(movies);
        }

        @Override
        public int getCount() {
            return movies.size();
        }

        @Override
        public Object getItem(int position) {
            return movies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return movies.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                grid = inflater.inflate(R.layout.movie_list_content, null);
            } else {
                grid = convertView;
            }
            final Movie movie = movies.get(position);
            ImageView imageView = (ImageView)grid.findViewById(R.id.imageView);
            Picasso.with(mContext).load(movie.getPosterPath()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_MOVIE_JSON, new Gson().toJson(movie));
                    mContext.startActivity(intent);
                }
            });
            return grid;
        }
    }
}
