package com.example.h071211039_finalmobile.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TvResponse {
    @SerializedName("results")
    private List<Tv> tvShows;

    public List<Tv> getTvShows() {
        return tvShows;
    }
}
