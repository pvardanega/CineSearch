package com.varda.android.cinesearch.resultsform.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieListAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> moviesList;
    private Activity context;

    public MovieListAdapter(Activity context, int textViewResourceId, ArrayList<Movie> movieDataItems) {
        super(context, textViewResourceId, movieDataItems);
        this.context = context;
        this.moviesList = movieDataItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.movie_data_row, null);
        }

        Movie movie = moviesList.get(position);

        if (movie != null) {

            // Name
            TextView nameTextView = (TextView) view.findViewById(R.id.movie_row_name);
            nameTextView.setText(movie.name);

            // Release
            TextView releaseTextView = (TextView) view.findViewById(R.id.movie_row_release);
            DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
            SimpleDateFormat sourceSdf = new SimpleDateFormat(this.context.getString(R.string.date_source_format));
            try {
                Date releaseDate = sourceSdf.parse(movie.released);
                releaseTextView.setText("Release date: " + sdf.format(releaseDate));
            } catch (ParseException e) {
                Log.w(getClass().getSimpleName(), "Error while parsing date " + movie.released, e);
                releaseTextView.setText("Release date: " + movie.released);
            }

            // Poster
            ImageView imageView = (ImageView) view.findViewById(R.id.movie_row_thumb);
            String posterUrl = movie.retrieveThumbnail();
            if (posterUrl == null) {
                imageView.setImageBitmap(null);
            } else {
                new BitmapDownloaderTask(imageView).execute(posterUrl);
            }

        }

        return view;
    }
}
