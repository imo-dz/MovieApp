package com.example.movieapp.Models;

public class MovieDetails {
    private int id;
    private String title;
    private String overview;
    private String release_date;
    private String poster_path;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public String getPosterPath() {
        return poster_path;
    }
    public String getReleaseYear() {
        return release_date != null && release_date.length() >= 4 ? release_date.substring(0, 4) : "N/A";
    }
}
