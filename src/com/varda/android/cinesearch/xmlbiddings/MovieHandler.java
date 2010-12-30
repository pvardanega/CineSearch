package com.varda.android.cinesearch.xmlbiddings;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MovieHandler extends DefaultHandler {

	private StringBuffer buffer = new StringBuffer();

	private ArrayList<Movie> moviesList;
	private Movie movie;
	private ArrayList<Image> movieImagesList;
	private Image movieImage;

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		this.buffer.setLength(0);
		if ("movies".equals(localName)) {
			this.moviesList = new ArrayList<Movie>();
		} else if ("movie".equals(localName)) {
			this.movie = new Movie();
		} else if ("images".equals(localName)) {
			this.movieImagesList = new ArrayList<Image>();
		} else if ("image".equals(localName)) {
			this.movieImage = new Image();
			this.movieImage.type = atts.getValue("type");
			this.movieImage.url = atts.getValue("url");
			this.movieImage.size = atts.getValue("size");
			this.movieImage.width = Integer.parseInt(atts.getValue("width"));
			this.movieImage.height = Integer.parseInt(atts.getValue("height"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException {
		if ("movie".equals(localName)) {
			this.moviesList.add(this.movie);
		} else if ("score".equals(localName)) {
			this.movie.score = this.buffer.toString();
		} else if ("popularity".equals(localName)) {
			this.movie.popularity = this.buffer.toString();
		} else if ("translatedlocalName".equals(localName)) {
			this.movie.translated = Boolean.valueOf(this.buffer.toString());
		} else if ("adult".equals(localName)) {
			this.movie.adult = Boolean.valueOf(this.buffer.toString());
		} else if ("language".equals(localName)) {
			this.movie.language = this.buffer.toString();
		} else if ("original_name".equals(localName)) {
			this.movie.originalName = this.buffer.toString();
		} else if ("name".equals(localName)) {
			this.movie.name = this.buffer.toString();
		} else if ("type".equals(localName)) {
			this.movie.type = this.buffer.toString();
		} else if ("id".equals(localName)) {
			this.movie.id = this.buffer.toString();
		} else if ("imdb_id".equals(localName)) {
			this.movie.imdbId = this.buffer.toString();
		} else if ("url".equals(localName)) {
			this.movie.url = this.buffer.toString();
		} else if ("votes".equals(localName)) {
			this.movie.votes = this.buffer.toString();
		} else if ("rating".equals(localName)) {
			this.movie.rating = this.buffer.toString();
		} else if ("certification".equals(localName)) {
			this.movie.certification = this.buffer.toString();
		} else if ("overview".equals(localName)) {
			this.movie.overview = this.buffer.toString();
		} else if ("released".equals(localName)) {
			this.movie.released = this.buffer.toString();
		} else if ("version".equals(localName)) {
			this.movie.version = this.buffer.toString();
		} else if ("last_modified_at".equals(localName)) {
			this.movie.lastModifiedAt = this.buffer.toString();
		} else if ("image".equals(localName)) {
			this.movieImagesList.add(this.movieImage);
		} else if ("images".equals(localName)) {
			this.movie.imagesList = this.movieImagesList;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		this.buffer.append(ch, start, length);
	}

	public ArrayList<Movie> retrieveMoviesList() {
		return this.moviesList;
	}
}
