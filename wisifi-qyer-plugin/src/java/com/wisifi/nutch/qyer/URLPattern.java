package com.wisifi.nutch.qyer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fwang on 1/5/15.
 */
public class URLPattern {
    public static String BASE_URL = "http://place\\.qyer\\.com/";

    // the regular expression for POI url
    public static String POI_REG = BASE_URL + "poi/([\\w]*?)/";
    public static Pattern POI_PATTERN = Pattern.compile(POI_REG + "$", Pattern.CASE_INSENSITIVE);

    // the regular expression for POI review url
    public static String POI_REVIEW_REG = POI_REG + "review/";
    public static Pattern POI_REVIEW_PATTERN = Pattern.compile(POI_REVIEW_REG + "$", Pattern.CASE_INSENSITIVE);

    public static boolean is_poi(String url) {
        return match(url, POI_PATTERN);
    }

    public static boolean is_poi_review(String url) {
        return match(url, POI_REVIEW_PATTERN);
    }

    private static boolean match(String text, Pattern pattern) {
        return pattern.matcher(text).matches();
    }

    public static void main(String[] args) {
        String test_url = "http://place.qyer.com/poi/V2UJY1FvBzVTbVI4/review/";
        boolean is_poi_url = is_poi(test_url);
        System.out.println("IS POI URL: " + is_poi_url);

        boolean is_poi_review_url = is_poi_review(test_url);
        System.out.println("IS POI REVIEW URL: " + is_poi_review_url);
    }
}
