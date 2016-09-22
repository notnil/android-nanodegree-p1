package com.loganspears.movies.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by logan on 9/20/16.
 */
public final class Movie {

    // date format: "2016-04-27"
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);

    @SerializedName("poster_path")
    private final String posterPath;

    @SerializedName("adult")
    private final boolean adult;

    @SerializedName("overview")
    private final String overview;

    @SerializedName("release_date")
    private final String releaseDate;

    @SerializedName("genre_ids")
    private final List<Integer> genreIds = new ArrayList<Integer>();

    @SerializedName("id")
    private final int id;

    @SerializedName("original_title")
    private final String originalTitle;

    @SerializedName("original_language")
    private final String originalLanguage;

    @SerializedName("title")
    private final String title;

    @SerializedName("backdrop_path")
    private final String backdropPath;

    @SerializedName("popularity")
    private final double popularity;

    @SerializedName("vote_count")
    private final int voteCount;

    @SerializedName("video")
    private final boolean video;

    @SerializedName("vote_average")
    private final double voteAverage;

    private Movie(String posterPath, boolean adult, String overview, String releaseDate, int id, String originalTitle, String originalLanguage, String title, String backdropPath, double popularity, int voteCount, boolean video, double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        try {
            Date date =  dateFormat.parse(releaseDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return "http://image.tmdb.org/t/p/w185" +backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}