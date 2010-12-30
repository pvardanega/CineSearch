package com.varda.android.cinesearch.resultsform;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.httpsearch.HttpRetriever;
import com.varda.android.cinesearch.xmlbiddings.Movie;
import com.varda.android.cinesearch.xmlbiddings.Person;

public class PersonInfoActivity extends Activity {

    private Person person;
    private String posterUrl;

    private TextView nameTextView;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_layout);

        this.person = (Person) getIntent().getSerializableExtra("person");
        this.setUI();
    }

    private void setUI() {
        HttpRetriever httpRetriever = new HttpRetriever();

        // Name
        this.nameTextView = (TextView) this.findViewById(R.id.person_name);
        this.nameTextView.setText(person.name);
        /*
        // Rating
        this.ratingTextView = (TextView) this.findViewById(R.id.movie_rate);
        this.ratingTextView.setText("Rating: " + movie.rating);

        // Release
        this.releaseTextView = (TextView) this.findViewById(R.id.movie_release_date);
        this.releaseTextView.setText("Release date: " + movie.released);

        // Language
        this.languageTextView = (TextView) this.findViewById(R.id.movie_lang);
        this.languageTextView.setText("Language: " + movie.language.toUpperCase());
        */

        // Thumb image
        this.imageView = (ImageView) this.findViewById(R.id.person_poster);
        this.posterUrl = person.retrieveThumbnail();
        if (this.posterUrl == null) {
            this.imageView.setImageBitmap(null);
        } else {
            this.imageView.setImageBitmap(httpRetriever.retrieveBitmap(this.posterUrl));
        }

        /*
        // Description
        this.descriptionTextView = (TextView) this.findViewById(R.id.movie_description);
        this.descriptionTextView.setText(movie.overview);
        */
    }

}
