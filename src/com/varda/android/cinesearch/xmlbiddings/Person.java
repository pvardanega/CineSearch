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
        builder.append(name);
        return builder.toString();
    }

}
