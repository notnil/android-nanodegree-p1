package com.loganspears.movies.model;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

/**
 * Created by logan on 9/29/16.
 */
public final class Review implements MovieDetail{

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

    public Intent getWebIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getUrl()));
        return intent;
    }

    @Override
    public String toString() {
        return getContent();
    }

    @Override
    public String getPrimaryContent() {
        return getContent();
    }

    @Override
    public String getSecondaryContent() {
        return getAuthor();
    }

    @Override
    public Intent getIntent() {
        return getWebIntent();
    }
}