package com.srikanth.sharkfeed.util;

/**
 * Handles the responsibility of creating the URL to call Flickr API.
 */
public class APIUtils {
    private final static String BASE_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    private final static String API_KEY = "api_key=949e98778755d1982f537d56236bbb42";
    // Tags with which the images are tagged
    private final static String TAG = "tags=";
    private final static String FORMAT = "format=json&nojsoncallback=1";
    private final static String PAGE = "page=";
    private final static String SORT = "sort=relevance";
    // Using a 3 collum span, this makes sure the last row is fully filled
    private final static String PER_PAGE = "per_page=99";
    // This makes sure we get only images
    private final static String CONTENT_TYPE = "content_type=1";
    private final static String EXTRAS = "extras=url_c,url_l,url_o,url_sq";
    private final static String SEPARATOR = "&";

    public static String buildUrlToQuery(String tagTerm, int page_number) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(BASE_URL);
        buffer.append(SEPARATOR);
        buffer.append(API_KEY);
        buffer.append(SEPARATOR);
        buffer.append(TAG);
        buffer.append(tagTerm);
        buffer.append(SEPARATOR);
        buffer.append(FORMAT);
        buffer.append(SEPARATOR);
        buffer.append(PAGE);
        buffer.append(page_number);
        buffer.append(SEPARATOR);
        buffer.append(SORT);
        buffer.append(SEPARATOR);
        buffer.append(PER_PAGE);
        buffer.append(SEPARATOR);
        buffer.append(CONTENT_TYPE);
        buffer.append(SEPARATOR);
        buffer.append(EXTRAS);
        return buffer.toString();
    }
}
