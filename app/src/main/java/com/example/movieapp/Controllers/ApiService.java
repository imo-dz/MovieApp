package com.example.movieapp.Controllers;
import com.example.movieapp.Models.MovieDetails;
import com.example.movieapp.Models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiService {
    @GET("discover/movie")
    Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
