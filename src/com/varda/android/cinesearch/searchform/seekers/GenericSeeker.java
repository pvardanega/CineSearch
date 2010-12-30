package com.varda.android.cinesearch.searchform.seekers;

import android.util.Log;
import com.varda.android.cinesearch.httpsearch.HttpRetriever;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public abstract class GenericSeeker<E> {

    protected static final String BASE_URL = "http://api.themoviedb.org/2.1/";
    protected static final String LANGUAGE_PATH = "en/";
    protected static final String XML_FORMAT = "xml/";
    protected static final String API_KEY = "c1e51d0e9a59526f000289bd0a7850ec";
    protected static final String SLASH = "/";

    protected HttpRetriever httpRetriever = new HttpRetriever();

    public abstract ArrayList<E> find(String query);
    public abstract ArrayList<E> find(String query, int maxResults);

    public abstract String retrieveSearchMethodPath();

    protected String constructSearchUrl(String query) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(BASE_URL);
        buffer.append(retrieveSearchMethodPath());
        buffer.append(LANGUAGE_PATH);
        buffer.append(XML_FORMAT);
        buffer.append(API_KEY);
        buffer.append(SLASH);
        try {
            buffer.append(URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.w(getClass().getSimpleName(), "Encoding not supported!", e);
        }
        return buffer.toString();
    }

    public ArrayList<E> retrieveFirstResults(ArrayList<E> list, int maxResults) {
        ArrayList<E> results = new ArrayList<E>();
        int count = Math.min(list.size(), maxResults);
        for (int i=0; i<count; i++) {
            results.add(list.get(i));
        }
        return results;
    }
}
