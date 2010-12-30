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
import com.varda.android.cinesearch.xmlbiddings.Movie;

import java.util.ArrayList;

public class MoviesListActivity extends ListActivity {

    private ArrayList<Movie> moviesList;
    private ArrayAdapter<Movie> moviesAdapter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_layout);

        this.moviesList = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        this.moviesAdapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, this.moviesList);
        setListAdapter(this.moviesAdapter);
    }

    @Override
    protected void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        Intent intent = new Intent(MoviesListActivity.this, MovieInfoActivity.class);
        intent.putExtra("movie", moviesAdapter.getItem(position));
        startActivity(intent);
    }

    private void longToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
