package com.varda.android.cinesearch.resultsform.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> moviesList;
    private Activity context;

    public MovieAdapter(Activity context, int textViewResourceId, ArrayList<Movie> movieDataItems) {
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
            releaseTextView.setText("Release date: " + movie.released);
        }

        return view;
    }
}
