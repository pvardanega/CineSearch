package com.varda.android.cinesearch.searchform.seekers;

import android.util.Log;
import android.util.Xml;
import com.varda.android.cinesearch.xmlbiddings.Person;
import com.varda.android.cinesearch.xmlbiddings.PersonHandler;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class PersonSeeker extends GenericSeeker<Person> {

    private static final String PEOPLE_SEARCH_PATH = "Person.search/";

    private ArrayList<Person> retrievePeopleList(String query) throws SAXException {
        String url = constructSearchUrl(query);
        String response = httpRetriever.retrieve(url);
        Log.d(getClass().getSimpleName(), response);
        PersonHandler personHandler = new PersonHandler();
        Xml.parse(response, personHandler);
        return personHandler.retrievePersonList();
    }

    @Override
    public ArrayList<Person> find(String query) {
        try {
            return this.retrievePeopleList(query);
        } catch (final SAXException e) {
            Log.e(getClass().getSimpleName(), "Error while executing query " + query, e);
        }
        return null;
    }

    @Override
    public ArrayList<Person> find(String query, int maxResults) {
        ArrayList<Person> results = this.find(query);
        return this.retrieveFirstResults(results, maxResults);
    }

    @Override
    public String retrieveSearchMethodPath() {
        return PEOPLE_SEARCH_PATH;
    }
}
