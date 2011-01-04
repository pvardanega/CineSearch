package com.varda.android.cinesearch.resultsform;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.httpsearch.HttpRetriever;
import com.varda.android.cinesearch.resultsform.adapters.BitmapDownloaderTask;
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieInfoActivity extends Activity {

    private Movie movie;
    private String posterUrl;

    private TextView nameTextView;
    private TextView ratingTextView;
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

    private void setUI() {
        HttpRetriever httpRetriever = new HttpRetriever();

        // Name
        this.nameTextView = (TextView) this.findViewById(R.id.movie_name);
        this.nameTextView.setText(movie.name);

        // Rating
        this.ratingTextView = (TextView) this.findViewById(R.id.movie_rate);
        this.ratingTextView.setText("Rating: " + movie.rating);

        // Release
        this.releaseTextView = (TextView) this.findViewById(R.id.movie_release_date);
        DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        SimpleDateFormat sourceSdf = new SimpleDateFormat(this.getString(R.string.date_source_format));
        try {
            Date releaseDate = sourceSdf.parse(movie.released);
            this.releaseTextView.setText("Release date: " + sdf.format(releaseDate));
        } catch (ParseException e) {
            Log.w(getClass().getSimpleName(), "Error while parsing date " + movie.released, e);
            this.releaseTextView.setText("Release date: " + movie.released);
        }

        // Language
        this.languageTextView = (TextView) this.findViewById(R.id.movie_lang);
        this.languageTextView.setText("Language: " + movie.language.toUpperCase());

        // Thumb image
        this.imageView = (ImageView) this.findViewById(R.id.movie_poster);
        this.posterUrl = movie.retrieveThumbnail();
        if (this.posterUrl == null) {
            this.imageView.setImageBitmap(null);
        } else {
            new BitmapDownloaderTask(this.imageView).execute(this.posterUrl);
        }

        // Description
        this.descriptionTextView = (TextView) this.findViewById(R.id.movie_description);
        this.descriptionTextView.setText(movie.overview);
    }
}
