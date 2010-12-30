package com.varda.android.cinesearch.searchform.seekers;

import android.util.Log;
import android.util.Xml;
import com.varda.android.cinesearch.xmlbiddings.Movie;
import com.varda.android.cinesearch.xmlbiddings.MovieHandler;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class MovieSeeker extends GenericSeeker<Movie> {

    private static final String MOVIE_SEARCH_PATH = "Movie.search/";

    private ArrayList<Movie> retrieveMoviesList(String query) throws SAXException {
        String url = constructSearchUrl(query);
        String response = httpRetriever.retrieve(url);
        Log.d(getClass().getSimpleName(), response);
        MovieHandler movieHandler = new MovieHandler();
        Xml.parse(response, movieHandler);
        return movieHandler.retrieveMoviesList();
    }

    @Override
    public ArrayList<Movie> find(String query) {
        try {
            return this.retrieveMoviesList(query);
        } catch (final SAXException e) {
            Log.e(getClass().getSimpleName(), "Error while executing query " + query, e);
        }
        return null;
    }

    @Override
    public ArrayList<Movie> find(String query, int maxResults) {
        ArrayList<Movie> results = this.find(query);
        return this.retrieveFirstResults(results, maxResults);
    }

    @Override
    public String retrieveSearchMethodPath() {
        return MOVIE_SEARCH_PATH;
    }
}
