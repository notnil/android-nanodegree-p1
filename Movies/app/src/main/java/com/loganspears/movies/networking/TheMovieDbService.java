package com.loganspears.movies.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by logan on 9/20/16.
 */
public interface TheMovieDbService {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies();

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies();

    @GET("movie/{id}/videos")
    Call<VideoResponse> getTrailers(@Path("id") String movieId);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") String movieId);
}
