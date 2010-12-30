package com.varda.android.cinesearch.xmlbiddings;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    public String score;
    public String popularity;
    public String name;
    public String id;
    public String biography;
    public String url;
    public String version;
    public String lastModifiedAt;
    public ArrayList<Image> imagesList;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("People [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
