package com.varda.android.cinesearch.resultsform;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.varda.android.cinesearch.R;
import com.varda.android.cinesearch.xmlbiddings.Person;

import java.util.ArrayList;

public class PersonsListActivity extends ListActivity {

    private ArrayAdapter<Person> personAdapter;
    private ArrayList<Person> personsList = new ArrayList<Person>();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persons_layout);

        this.personsList = (ArrayList<Person>) getIntent().getSerializableExtra("persons");
        this.personAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, this.personsList);
        setListAdapter(this.personAdapter);
    }

    @Override
    protected void onListItemClick(ListView lv, View view, int position, long id) {
        super.onListItemClick(lv, view, position, id);
        Intent intent = new Intent(PersonsListActivity.this, PersonInfoActivity.class);
        intent.putExtra("person", personAdapter.getItem(position));
        startActivity(intent);
    }
}
