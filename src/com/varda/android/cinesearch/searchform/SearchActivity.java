package com.varda.android.cinesearch.searchform;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.resultsform.MovieInfoActivity;
import com.varda.android.cinesearch.resultsform.MoviesListActivity;
import com.varda.android.cinesearch.resultsform.PersonInfoActivity;
import com.varda.android.cinesearch.resultsform.PersonsListActivity;
import com.varda.android.cinesearch.searchform.seekers.GenericSeeker;
import com.varda.android.cinesearch.searchform.seekers.MovieSeeker;
import com.varda.android.cinesearch.searchform.seekers.PersonSeeker;
import com.varda.android.cinesearch.xmlbiddings.Movie;
import com.varda.android.cinesearch.xmlbiddings.Person;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private static final String EMPTY_STRING = "";

    private EditText searchEditText;
    private RadioButton moviesSearchRadioButton;
    private RadioButton peopleSearchRadioButton;
    private Button searchButton;
    private Button resetButton;

    private ProgressDialog progressDialog;

    private GenericSeeker<Movie> movieSeeker = new MovieSeeker();
    private GenericSeeker<Person> personSeeker = new PersonSeeker();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Get components from view
        this.findAllViewsById();

        // Set evenListener for search Button
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String query = searchEditText.getText().toString();
                performSearch(query);
            }
        });

        // Set evenListener for reset Button
        this.resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                searchEditText.setText(EMPTY_STRING);
                searchEditText.setTextColor(Color.BLACK);
            }
        });

        // Set eventListener for search EditText
        this.searchEditText.setOnFocusChangeListener(new DftTextOnFocusListener(getString(R.string.search)));
    }

    private void findAllViewsById() {
        this.searchEditText = (EditText) this.findViewById(R.id.search_edit_text);
        this.moviesSearchRadioButton = (RadioButton) this.findViewById(R.id.movie_search_radio_button);
        this.peopleSearchRadioButton = (RadioButton) this.findViewById(R.id.people_search_radio_button);
        this.searchButton = (Button) this.findViewById(R.id.search_button);
        this.resetButton = (Button) this.findViewById(R.id.reset_button);
    }

    private class DftTextOnFocusListener implements View.OnFocusChangeListener {
        private String defaultMessage;

        public DftTextOnFocusListener(String defaultMessage) {
            this.defaultMessage = defaultMessage;
        }

        public void onFocusChange(View view, boolean hasFocus) {
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                if (hasFocus) {
                    if (editText.getText().toString().equals(this.defaultMessage)) {
                        editText.setText(EMPTY_STRING);
                        editText.setTextColor(Color.BLACK);
                    }
                } else {
                    if (editText.getText().toString().equals(EMPTY_STRING)) {
                        editText.setText(this.defaultMessage);
                        editText.setTextColor(Color.GRAY);
                    }
                }
            }
        }
    }

    private void performSearch(String query) {
        this.progressDialog = ProgressDialog.show(SearchActivity.this, "Please wait... ", "Retrieving data...", true, true);
        if (moviesSearchRadioButton.isChecked()) {
            PerformMovieSearchTask task = new PerformMovieSearchTask();
            task.execute(query);
            progressDialog.setOnCancelListener(new OnCancelSearchListener(task));
        } else if (peopleSearchRadioButton.isChecked()) {
            PerformPeopleSearchTask task = new PerformPeopleSearchTask();
            task.execute(query);
            progressDialog.setOnCancelListener(new OnCancelSearchListener(task));
        }
    }

    private void longToast(String message) {
        Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private class PerformMovieSearchTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        protected ArrayList<Movie> doInBackground(String... params) {
            String query = params[0];
            return movieSeeker.find(query);
        }

        protected void onPostExecute(final ArrayList<Movie> results) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    if (results != null) {
                        if (results.size() == 0) {
                            longToast(getString(R.string.no_movie_found));
                        } else if (results.size() == 1) {
                            Intent intent = new Intent(SearchActivity.this, MovieInfoActivity.class);
                            intent.putExtra("movie", results.get(0));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SearchActivity.this, MoviesListActivity.class);
                            intent.putExtra("movies", results);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    private class PerformPeopleSearchTask extends AsyncTask<String, Void, ArrayList<Person>> {
        protected ArrayList<Person> doInBackground(String... params) {
            String query = params[0];
            return personSeeker.find(query);
        }

        protected void onPostExecute(final ArrayList<Person> results) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    if (results != null) {
                        if (results.size() == 0) {
                            longToast(getString(R.string.no_person_found));
                        } else if (results.size() == 1) {
                            Intent intent = new Intent(SearchActivity.this, PersonInfoActivity.class);
                            intent.putExtra("person", results.get(0));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SearchActivity.this, PersonsListActivity.class);
                            intent.putExtra("persons", results);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    private class OnCancelSearchListener implements DialogInterface.OnCancelListener {
        private AsyncTask<?, ?, ?> task;

        public OnCancelSearchListener(AsyncTask<?, ?, ?> task) {
            this.task = task;
        }

        public void onCancel(DialogInterface dialogInterface) {
            if (task != null) {
                task.cancel(true);
            }
        }
    }
}
