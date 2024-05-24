package com.example.movieapp.Views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.Controllers.ApiClient;
import com.example.movieapp.Controllers.ApiService;
import com.example.movieapp.Models.MovieDetails;
import com.example.movieapp.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title, overview, releaseDate;
    private ApiService apiService;
    private final String API_KEY = "c9856d0cb57c3f14bf75bdc6c063b8f3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        poster = findViewById(R.id.movie_detail_poster);
        title = findViewById(R.id.movie_detail_title);
        overview = findViewById(R.id.movie_detail_overview);
        releaseDate = findViewById(R.id.movie_detail_release_date);

        int movieId = getIntent().getIntExtra("movie_id", 0);

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchMovieDetails(movieId);
    }

    private void fetchMovieDetails(int movieId) {
        apiService.getMovieDetails(movieId, API_KEY).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetails movie = response.body();
                    title.setText(movie.getTitle());
                    overview.setText(movie.getOverview());
                    releaseDate.setText(movie.getReleaseYear());

                    String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                    Picasso.get().load(posterUrl).into(poster);
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Failed to fetch movie details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
