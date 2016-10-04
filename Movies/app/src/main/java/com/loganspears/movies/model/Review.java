package com.loganspears.movies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by logan on 9/29/16.
 */
public final class Review {

    @SerializedName("id")
    private final String id;

    @SerializedName("author")
    private final String author;

    @SerializedName("content")
    private final String content;

    @SerializedName("url")
    private final String url;

    private Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}