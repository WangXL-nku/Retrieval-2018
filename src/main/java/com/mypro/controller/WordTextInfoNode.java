package com.mypro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordTextInfoNode {
    private int MovieId;

    public int getMovieId() {
        return MovieId;
    }

    public WordTextInfoNode() {
        Line = new ArrayList<String>();
    }

    public WordTextInfoNode(int movieId, List<String> line) {
        MovieId = movieId;
        Line = line;

    }

    public void setMovieId(int movieId) {
        MovieId = movieId;
    }

    public List<String> getLine() {
        return Line;
    }

    public void setLine(List<String> line) {
        Line = line;
    }

    private List<String> Line;
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordTextInfoNode wordo = (WordTextInfoNode) o;
        return Objects.equals(getMovieId(), wordo.getMovieId());
    }
}
