package com.varda.android.cinesearch.resultsform;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.resultsform.adapters.MovieListAdapter;
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.util.ArrayList;

public class MoviesListActivity extends ListActivity {

    private MovieListAdapter moviesListAdapter;
    private ArrayList<Movie> moviesList = new ArrayList<Movie>();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_layout);

        this.moviesListAdapter = new MovieListAdapter(this, R.layout.movie_data_row, this.moviesList);
        this.moviesList = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        setListAdapter(this.moviesListAdapter);

        if (this.moviesList != null && !this.moviesList.isEmpty()) {
            this.moviesListAdapter.notifyDataSetChanged();
            this.moviesListAdapter.clear();
            for (Movie movie : this.moviesList) {
                this.moviesListAdapter.add(movie);
            }
        }

        this.moviesListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        Intent intent = new Intent(MoviesListActivity.this, MovieInfoActivity.class);
        intent.putExtra("movie", moviesListAdapter.getItem(position));
        startActivity(intent);
    }
}
