package com.varda.android.cinesearch.xmlbiddings;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {

    public String score;
    public String popularity;
    public boolean translated;
    public boolean adult;
    public String language;
    public String originalName;
    public String name;
    public String type;
    public String id;
    public String imdbId;
    public String url;
    public String votes;
    public String rating;
    public String certification;
    public String overview;
    public String released;
    public String version;
    public String lastModifiedAt;
    public ArrayList<Image> imagesList;

    public String retrieveThumbnail() {
        if (imagesList != null && !imagesList.isEmpty()) {
            for (Image image : imagesList) {
                if (image.size.equalsIgnoreCase(Image.SIZE_THUMB)
                    && image.type.equalsIgnoreCase(Image.TYPE_POSTER)) {
                    return image.url;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Movie [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
