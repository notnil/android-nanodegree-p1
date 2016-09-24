package com.loganspears.movies.model;

/**
 * Created by logan_000 on 9/24/2016.
 */
public enum MovieOrder {
    NO_ORDER("No Order"),
    MOST_POPULAR("Most Popular"),
    HIGHEST_RATED("Highest Rated"),;

    private final String name;

    MovieOrder(String s) {
        this.name = s;
    }

    public static MovieOrder fromString(String s) {
        if (s == null) {
            return NO_ORDER;
        }
        for (MovieOrder movieOrder : MovieOrder.values()) {
            if (s.equals(movieOrder.getName())){
                return movieOrder;
            }
        }
        return NO_ORDER;
    }

    public String getName() {
        return name;
    }
}
