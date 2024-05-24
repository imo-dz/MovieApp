package com.example.movieapp.Views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Controllers.ApiClient;
import com.example.movieapp.Controllers.ApiService;
import com.example.movieapp.Models.Movie;
import com.example.movieapp.Models.MovieResponse;
import com.example.movieapp.Controllers.MovieAdapter;
import com.example.movieapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ApiService apiService;
    private final String API_KEY = "c9856d0cb57c3f14bf75bdc6c063b8f3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchTrendingMovies();
    }

    private void fetchTrendingMovies() {
        apiService.getTrendingMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    adapter = new MovieAdapter(MainActivity.this, movies, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }
}
