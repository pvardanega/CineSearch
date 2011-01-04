package com.varda.android.cinesearch.resultsform;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.varda.android.cinesearch.R;
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
    private ImageView posterImageView;
    private ImageView ratingImageView;
    private TextView descriptionTextView;

    private static Locale[] locales = Locale.getAvailableLocales();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);

        this.movie = (Movie) getIntent().getSerializableExtra("movie");
        this.setUI();
        this.setVisible(true);
    }

    private void setUI() {

        // Name
        this.nameTextView = (TextView) this.findViewById(R.id.movie_name);
        this.nameTextView.setText(movie.name);

        // Rating
        this.ratingTextView = (TextView) this.findViewById(R.id.movie_rate);
        this.ratingTextView.setText(" (" + movie.rating + ")");
        this.ratingImageView = (ImageView) this.findViewById(R.id.movie_rate_stars);
        this.setStarToButtons();

        // Release
        this.releaseTextView = (TextView) this.findViewById(R.id.movie_release_date);
        DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        SimpleDateFormat sourceSdf = new SimpleDateFormat(this.getString(R.string.date_source_format));
        try {
            Date releaseDate = sourceSdf.parse(movie.released);
            this.releaseTextView.setText(getString(R.string.release_date)+ " " + sdf.format(releaseDate));
        } catch (ParseException e) {
            Log.w(getClass().getSimpleName(), "Error while parsing date " + movie.released, e);
            this.releaseTextView.setText(getString(R.string.release_date)+ " " + movie.released);
        }

        // Language
        this.languageTextView = (TextView) this.findViewById(R.id.movie_lang);
        StringBuilder language = new StringBuilder();
        language.append(getString(R.string.language) + " ");
        Locale locale = getLocaleFromLanguage(movie.language);
        StringBuilder localeCountryName = new StringBuilder(locale.getDisplayName());
        if (locale == null || "".equals(localeCountryName)) {
            language.append(movie.language.toUpperCase());
        } else {
            // Majuscule sur la première lettre
            language.append(localeCountryName.replace(0, 1, localeCountryName.substring(0, 1).toUpperCase()));
        }
        this.languageTextView.setText(language);

        // Thumb image
        this.posterImageView = (ImageView) this.findViewById(R.id.movie_poster);
        this.posterUrl = movie.retrieveThumbnail();
        if (this.posterUrl == null) {
            this.posterImageView.setImageBitmap(null);
        } else {
            new BitmapDownloaderTask(this.posterImageView).execute(this.posterUrl);
        }

        // Description
        this.descriptionTextView = (TextView) this.findViewById(R.id.movie_description);
        this.descriptionTextView.setText(movie.overview);
    }

    private void setStarToButtons() {
        double rate = Double.valueOf(movie.rating) / 2.0;

        if (rate >= 0.5 && rate < 1.5) {
            this.ratingImageView.setImageResource(R.drawable.star1);
        } else if (rate >= 1.5 && rate < 2.5) {
            this.ratingImageView.setImageResource(R.drawable.star2);
        } else if (rate >= 2.5 && rate < 3.5) {
            this.ratingImageView.setImageResource(R.drawable.star3);
        } else if (rate >= 3.5 && rate < 4.5) {
            this.ratingImageView.setImageResource(R.drawable.star4);
        } else if (rate >= 4.5) {
            this.ratingImageView.setImageResource(R.drawable.star5);
        }
    }

    private Locale getLocaleFromLanguage(String lang) {
        for (Locale locale : locales) {
            if (lang.equals(locale.getLanguage())) {
                return locale;
            }
        }
        return null;
    }
}
