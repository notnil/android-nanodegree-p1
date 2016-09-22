package com.loganspears.movies.networking;

import android.preference.PreferenceActivity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.loganspears.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logan on 9/20/16.
 */
public class MovieResponse {

    @SerializedName("page")
    private final int page;

    @SerializedName("total_results")
    private final int totalResults;

    @SerializedName("total_pages")
    private final int totalPages;

    @SerializedName("results")
    private final List<Movie> results;

    private MovieResponse(int page, int totalResults, int totalPages, List<Movie> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getResults() {
        return new ArrayList<>(results);
    }
}
