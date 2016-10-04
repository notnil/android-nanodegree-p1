package com.loganspears.movies.networking;

import com.google.gson.annotations.SerializedName;
import com.loganspears.movies.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {

    @SerializedName("id")
    private final int id;
    @SerializedName("results")
    private final List<Review> results;

    private ReviewResponse(int id, List<Review> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public List<Review> getResults() {
        return new ArrayList<>(results);
    }
}