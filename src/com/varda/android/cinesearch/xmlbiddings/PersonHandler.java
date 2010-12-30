package com.varda.android.cinesearch.xmlbiddings;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class PersonHandler extends DefaultHandler {

    private StringBuffer buffer = new StringBuffer();
    private ArrayList<Person> personsList;
    private Person person;
    private ArrayList<Image> personImagesList;
    private Image personImage;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        buffer.setLength(0);

        if ("people".equals(localName)) {
            this.personsList = new ArrayList<Person>();
        } else if ("person".equals(localName)) {
            this.person = new Person();
        } else if ("images".equals(localName)) {
            this.personImagesList = new ArrayList<Image>();
        } else if ("image".equals(localName)) {
            this.personImage = new Image();
            this.personImage.type = atts.getValue("type");
            this.personImage.url = atts.getValue("url");
            this.personImage.size = atts.getValue("size");
            this.personImage.width = Integer.valueOf(atts.getValue("width"));
            this.personImage.height = Integer.valueOf(atts.getValue("height"));
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("person".equals(localName)) {
            this.personsList.add(this.person);
        } else if ("score".equals(localName)) {
            this.person.score = this.buffer.toString();
        } else if ("popularity".equals(localName)) {
            this.person.popularity = this.buffer.toString();
        } else if ("name".equals(localName)) {
            this.person.name = this.buffer.toString();
        } else if ("id".equals(localName)) {
            this.person.id = this.buffer.toString();
        } else if ("biography".equals(localName)) {
            this.person.biography = this.buffer.toString();
        } else if ("url".equals(localName)) {
            this.person.url = this.buffer.toString();
        } else if ("version".equals(localName)) {
            this.person.version = this.buffer.toString();
        } else if ("last_modified_at".equals(localName)) {
            this.person.lastModifiedAt = this.buffer.toString();
        } else if ("image".equals(localName)) {
            this.personImagesList.add(this.personImage);
        } else if ("images".equals(localName)) {
            this.person.imagesList = this.personImagesList;
        }
    }

    public void characters(char[] ch, int start, int length) {
        this.buffer.append(ch, start, length);
    }

    public ArrayList<Person> retrievePersonList() {
        return this.personsList;
    }
}
