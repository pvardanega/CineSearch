package com.varda.android.cinesearch.xmlbiddings;

import java.io.Serializable;

public class Image implements Serializable {

    public static final String SIZE_ORIGINAL = "original";
    public static final String SIZE_MID = "mid";
    public static final String SIZE_COVER = "cover";
    public static final String SIZE_THUMB = "thumb";

    public static final String TYPE_PROFILE = "profile";
    public static final String TYPE_POSTER = "poster";

    public String type;
    public String url;
    public String size;
    public int width;
    public int height;

}
