package com.test;

public class Constants {
    public static final String CACHE_FILE_NAME = "feed.json";
    public static final String API_KEY_TOP_STORY = "4de3eeaa8e1f7c031d6c532aa2107613:18:73366674";
    public static final String API_KEY_SEARCH = "c3b1e59cd255bd3bb03e482104558419:1:73366674";
    public static final String URL_TOP_STORY = "http://api.nytimes.com/svc/topstories/v1/%s.json?api-key=" + API_KEY_TOP_STORY;
    public static final String URL_SEARCH = "http://api.nytimes.com/svc/search/v2/articlesearch.json?q=%s&api-key=" + API_KEY_SEARCH;
}
