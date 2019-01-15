package com.example.helsanf.catalogemovie.getModel;

import com.example.helsanf.catalogemovie.model.MovieItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getMovie {
    @SerializedName("results")
    private List<MovieItem> results = null;

    public List<MovieItem> getResults() {
        return results;
    }

    public void setResults(List<MovieItem> results) {
        this.results = results;
    }
}
