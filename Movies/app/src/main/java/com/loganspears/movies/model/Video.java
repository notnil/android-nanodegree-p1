package com.loganspears.movies.model;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by logan on 9/29/16.
 */
public final class Video implements MovieDetail{
    private static final String VIDEO_SITE_YOUTUBE = "YouTube";
    private static final String VIDEO_TYPE_TRAILER = "Trailer";

    private final String key;
    private final String name;
    private final String site;
    private final String type;

    private Video(String key, String name, String site, String type) {
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public String getSite() {
        return site;
    }

    private boolean isYoutubeVideo() {
        return VIDEO_SITE_YOUTUBE.equals(site) && VIDEO_TYPE_TRAILER.equals(type) && key != null;
    }

    public Intent getYoutubeIntent() {
        String url = "https://www.youtube.com?v=" + key;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }


    @Override
    public String toString() {
        return "Video{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public String getPrimaryContent() {
        return getName();
    }

    @Override
    public String getSecondaryContent() {
        return getSite();
    }

    @Override
    public Intent getIntent() {
        if (isYoutubeVideo()) {
            return getYoutubeIntent();
        }
        return null;
    }
}
