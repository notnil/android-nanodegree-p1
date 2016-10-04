package com.loganspears.movies.model;

import android.content.Intent;

/**
 * Created by logan_000 on 10/3/2016.
 */

public interface MovieDetail {
    String getPrimaryContent();
    String getSecondaryContent();
    Intent getIntent();
}
