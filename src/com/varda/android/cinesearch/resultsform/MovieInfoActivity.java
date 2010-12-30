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

    private static final String IMDB_BASE_URL = "http://m.imdb.com/title/";
    private Movie movie;
    private String posterUrl;

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
        // Name
        TextView nameTextView = (TextView) this.findViewById(R.id.movie_name);
        nameTextView.setText(movie.name);
        // Rating
        TextView ratingTextView = (TextView) this.findViewById(R.id.movie_rate);
        ratingTextView.setText("Rating: " + movie.rating);
        // Popularity
        TextView popularityTextView = (TextView) this.findViewById(R.id.movie_popularity);
        ratingTextView.setText("Popularity: " + movie.popularity);
        // Score
        TextView scoreTextView = (TextView) this.findViewById(R.id.movie_score);
        ratingTextView.setText("Score: " + movie.score);
        // Release
        TextView releaseTextView = (TextView) this.findViewById(R.id.movie_release_date);
        releaseTextView.setText("Release date:" + movie.released);
        // Language
        TextView languageTextView = (TextView) this.findViewById(R.id.movie_lang);
        languageTextView.setText("Language:" + movie.language);
        // Thumb image
        ImageView imageView = (ImageView) this.findViewById(R.id.movie_poster);
        this.posterUrl = movie.retrieveThumbnail();

        if (this.posterUrl != null) {
            new BitmapDownloaderTask(imageView).execute(this.posterUrl);
        } else {
            imageView.setImageBitmap(null);
        }
        // Description
        TextView descriptionTextView = (TextView) this.findViewById(R.id.movie_description);
        languageTextView.setText(movie.overview);
    }

    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private String url;
        private final WeakReference<ImageView> imageViewWeakReference;
        private HttpRetriever httpRetriever = new HttpRetriever();

        public BitmapDownloaderTask(ImageView imageView) {
            this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            this.url = params[0];
            InputStream in = httpRetriever.retrieveStream(url);
            if (in == null) {
                return null;
            }
            return BitmapFactory.decodeStream(new FlushedInputStream(in));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (this.imageViewWeakReference != null) {
                ImageView imageView = this.imageViewWeakReference.get();
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
