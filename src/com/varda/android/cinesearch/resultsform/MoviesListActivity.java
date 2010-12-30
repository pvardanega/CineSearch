package com.varda.android.cinesearch.resultsform;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.resultsform.adapters.MovieAdapter;
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.util.ArrayList;

public class MoviesListActivity extends ListActivity {

    private MovieAdapter moviesAdapter;
    private ArrayList<Movie> moviesList = new ArrayList<Movie>();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_layout);

        this.moviesAdapter = new MovieAdapter(this, R.layout.movie_data_row, this.moviesList);
        this.moviesList = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        setListAdapter(this.moviesAdapter);

        if (this.moviesList != null && !this.moviesList.isEmpty()) {
            this.moviesAdapter.notifyDataSetChanged();
            this.moviesAdapter.clear();
            for (Movie movie : this.moviesList) {
                this.moviesAdapter.add(movie);
            }
        }

        this.moviesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        Intent intent = new Intent(MoviesListActivity.this, MovieInfoActivity.class);
        intent.putExtra("movie", moviesAdapter.getItem(position));
        startActivity(intent);
    }
}
