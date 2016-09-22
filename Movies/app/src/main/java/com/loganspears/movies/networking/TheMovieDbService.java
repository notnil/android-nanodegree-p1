package com.loganspears.movies.networking;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by logan on 9/20/16.
 */
public interface TheMovieDbService {
    @GET("popular")
    Call<MovieResponse> getPopularMovies();

    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies();
}
