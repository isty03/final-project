package com.example.h071211039_finalmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.h071211039_finalmobile.model.Favorite;
import com.example.h071211039_finalmobile.model.Movie;
import com.example.h071211039_finalmobile.model.Tv;
import com.example.h071211039_finalmobile.database.DatabaseHelper;

public class FilmDetail extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ImageView backdropImageView, backButton, favoriteButton, posterImageView, typeImageView;
    private TextView titleTextView, releaseDateTextView, ratingTextView, synopsisTextView;
    boolean favorite = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        backdropImageView = findViewById(R.id.iv_backdrop);
        backButton = findViewById(R.id.btn_back);
        favoriteButton = findViewById(R.id.btn_favorite);
        posterImageView = findViewById(R.id.iv_poster);
        titleTextView = findViewById(R.id.tv_title);
        releaseDateTextView = findViewById(R.id.tv_release_date);
        ratingTextView = findViewById(R.id.tv_rating);
        typeImageView = findViewById(R.id.iv_type);
        synopsisTextView = findViewById(R.id.tv_synopsis);
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null) {
            Movie movie = intent.getParcelableExtra("movie");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getBackdropUrl();
            titleTextView.setText(movie.getTitle());
            ratingTextView.setText(movie.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(posterImageView);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(backdropImageView);
            synopsisTextView.setText(movie.getOverview());

            favoriteButton.setOnClickListener(view -> {
                if (!favorite) {
                    favoriteButton.setImageResource(R.drawable.ic_favorite);
                    favorite = true;
                    addMovieToFavorites(movie.getId(), movie.getOverview(), posterUrl, movie.getReleaseDate(), movie.getTitle(), movie.getVoteAverage(), backdropUrl);
                } else {
                    favoriteButton.setImageResource(R.drawable.ic_favorite_border);
                    favorite = false;
                    deleteMovieToFavorites(movie.getTitle());
                }
            });


        } else if (intent.getParcelableExtra("show") != null) {
            Tv show = intent.getParcelableExtra("show");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + show.getPosterUrl();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + show.getBackdropUrl();
            titleTextView.setText(show.getName());
            ratingTextView.setText(show.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(posterImageView);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(backdropImageView);
            synopsisTextView.setText(show.getOverview());

            favoriteButton.setOnClickListener(view -> {
                if (!favorite) {
                    favoriteButton.setImageResource(R.drawable.ic_favorite);
                    favorite = true;
                    addMovieToFavorites(show.getId(), show.getOverview(), posterUrl, show.getName(), show.getName(), show.getVoteAverage(), backdropUrl);
                } else {
                    favoriteButton.setImageResource(R.drawable.ic_favorite_border);
                    favorite = false;
                    deleteMovieToFavorites(show.getName());
                }
            });
        }
        else {
            Favorite favorite = intent.getParcelableExtra("favorite");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getBackdropUrl();
            titleTextView.setText(favorite.getTitle());
            ratingTextView.setText(favorite.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(posterImageView);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(backdropImageView);
            synopsisTextView.setText(favorite.getOverview());

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dbHelper.isMovieInFavorites(favorite.getTitle())) {
                        addMovieToFavorites(favorite.getId(), favorite.getOverview(), posterUrl, favorite.getTitle(), favorite.getTitle(), favorite.getVoteAverage(), backdropUrl);
                    } else {
                        deleteMovieToFavorites(favorite.getTitle());
                    }
                }
            });
        }
        backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void addMovieToFavorites(int id, String overview, String posterUrl, String releaseDate, String title, double voteAverage, String backdropUrl) {
        Movie movie = new Movie(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
        long result = dbHelper.insertMovie(movie);
        if (result != -1) {
            Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add movie", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteMovieToFavorites(String nama) {
        long result = dbHelper.deleteMovie(nama);
        if (result != -1) {
            Toast.makeText(this, "Movie Deleted to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete movie", Toast.LENGTH_SHORT).show();
        }
    }
}
