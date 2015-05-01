package com.wisifi.nutch.qyer;

import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by fwang on 1/5/15.
 */
public class FieldExtractor {
    public static final Logger LOG = LoggerFactory.getLogger("com.wisifi.nutch.qyer.fieldextractor");

    public static Map<String, String> FIELDS = Maps.newHashMap();

    public void init() {
        FIELDS.put("poi_titile", ".poiDet-largeTit");
        FIELDS.put("poi_stars", ".infos .points");
    }

    public Map<String, Object> extractPOIFields(String url, String html) {
        Map<String, Object> results = Maps.newHashMap();
        String poi_title = this.extract_element(html, FIELDS.get("poi_title")).text();
        results.put("poi_titile", poi_title);

        float poi_stars = Float.parseFloat(this.extract_element(html, FIELDS.get("poi_stars")).text());
        results.put("poi_stars", poi_stars);
        return null;
    }

    /**
     * Perform Jsoup
     * @param html
     */
    private Element extract_element(String html,String query){

        try {

            org.jsoup.nodes.Document doc2  = Jsoup.parse(html);
            if(!query.equals("")){
                Elements elts = doc2.select(query);
                if (elts.size() > 1) {
                    LOG.error("Oops: JSON Query is Error (multiple elements identified): " + query);
                }
                return elts.get(0);
            }
            else return null;

        }
        catch (Exception e) {
            LOG.error("Query: " + query);
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Perform Jsoup
     * @param html
     */
    private Elements extract_elements(String html,String query){

        try {

            org.jsoup.nodes.Document doc2  = Jsoup.parse(html);
            if(!query.equals("")){
                Elements viewedEl = doc2.select(query);
                return viewedEl;
            }
            else return null;

        }
        catch (Exception e) {
            LOG.error("Query: " + query);
            e.printStackTrace();
            return null;
        }

    }

}
