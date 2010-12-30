package com.varda.android.cinesearch.resultsform;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.httpsearch.FlushedInputStream;
import com.varda.android.cinesearch.httpsearch.HttpRetriever;
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.io.InputStream;
import java.lang.ref.WeakReference;

public class MovieInfoActivity extends Activity {

    private Movie movie;
    private String posterUrl;

    private TextView nameTextView;
    private TextView ratingTextView;
    private TextView popularityTextView;
    private TextView scoreTextView;
    private TextView releaseTextView;
    private TextView languageTextView;
    private ImageView imageView;
    private TextView descriptionTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);

        this.movie = (Movie) getIntent().getSerializableExtra("movie");
        this.setUI();
    }

    private void longToast(String message) {
        Toast.makeText(MovieInfoActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void setUI() {
        HttpRetriever httpRetriever = new HttpRetriever();

        // Name
        this.nameTextView = (TextView) this.findViewById(R.id.movie_name);
        this.nameTextView.setText(movie.name);

        // Rating
        this.ratingTextView = (TextView) this.findViewById(R.id.movie_rate);
        this.ratingTextView.setText("Rating: " + movie.rating);

        // Popularity
        this.popularityTextView = (TextView) this.findViewById(R.id.movie_popularity);
        this.popularityTextView.setText("Popularity: " + movie.popularity);

        // Score
        this.scoreTextView = (TextView) this.findViewById(R.id.movie_score);
        this.scoreTextView.setText("Score: " + movie.score);

        // Release
        this.releaseTextView = (TextView) this.findViewById(R.id.movie_release_date);
        this.releaseTextView.setText("Release date: " + movie.released);

        // Language
        this.languageTextView = (TextView) this.findViewById(R.id.movie_lang);
        this.languageTextView.setText("Language: " + movie.language);

        // Thumb image
        this.imageView = (ImageView) this.findViewById(R.id.movie_poster);
        this.posterUrl = movie.retrieveThumbnail();
        if (this.posterUrl == null) {
            this.imageView.setImageBitmap(null);
        } else {
            this.imageView.setImageBitmap(httpRetriever.retrieveBitmap(this.posterUrl));
        }

        // Description
        this.descriptionTextView = (TextView) this.findViewById(R.id.movie_description);
        this.descriptionTextView.setText(movie.overview);
    }
}
