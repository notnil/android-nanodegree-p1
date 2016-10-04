package com.loganspears.movies.networking;

import com.google.gson.annotations.SerializedName;
import com.loganspears.movies.model.Video;

import java.util.ArrayList;
import java.util.List;

public final class VideoResponse {

    @SerializedName("id")
    private final int id;

    @SerializedName("results")
    private final List<Video> videoList;

    private VideoResponse(int id, List<Video> videoList) {
        this.id = id;
        this.videoList = videoList;
    }

    public int getId() {
        return id;
    }

    public List<Video> getVideoList() {
        return new ArrayList<>(videoList);
    }
}