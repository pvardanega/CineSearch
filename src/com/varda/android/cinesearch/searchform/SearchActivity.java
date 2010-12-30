package com.varda.android.cinesearch.searchform;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.varda.android.cinesearch.R;

public class SearchActivity extends Activity {

    private static final String EMPTY_STRING = "";

    private EditText searchEditText;
    private RadioButton moviesSearchRadioButton;
    private RadioButton peopleSearchRadioButton;
    private RadioGroup searchRadioGroup;
    private Button searchButton;
    private Button resetButton;

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
                //performSearch(query);
                Toast.makeText(SearchActivity.this, "BLOB", Toast.LENGTH_LONG).show();
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
        this.searchRadioGroup = (RadioGroup) this.findViewById(R.id.search_radio_group);
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
}
