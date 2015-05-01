package com.wisifi.nutch.qyer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by fwang on 1/5/15.
 */
public class FieldExtractor {
    public static final Logger LOG = LoggerFactory.getLogger("com.wisifi.nutch.qyer.fieldextractor");

    public static Map<String, String> FIELDS = Maps.newHashMap();

    public void init() {
        FIELDS.put("poi_titile", ".poiDet-largeTit"); // POI title query
        FIELDS.put("poi_stars", ".infos .points"); // POI star rating query
        FIELDS.put("poi_desc", ".poiDet-detail"); // POI description query
        FIELDS.put("poi_tips", ".poiDet-tips li"); // POI tips query
        FIELDS.put("poi_gmap", ".map img"); // POI gogole image url
    }

    public Map<String, Object> extractPOIFields(String url, String html) {
        Map<String, Object> results = Maps.newHashMap();

        Element element = this.extract_element(html, FIELDS.get("poi_title"));

        String poi_title = element != null? element.text() : "NA";
        results.put("poi_titile", poi_title);

        element = this.extract_element(html, FIELDS.get("poi_stars"));
        float poi_stars = element != null ? Float.parseFloat(element.text()) : 0;
        results.put("poi_stars", poi_stars);

        element = this.extract_element(html, FIELDS.get("poi_desc"));
        String poi_desc = element != null ? element.text() : "NA";
        results.put("poi_desc", poi_desc);

        List<String> poi_tips = Lists.newArrayList();

        Elements tips_es = this.extract_elements(html, FIELDS.get("poi_tips"));
        for (Element e : tips_es) {
            poi_tips.add(e.text());
        }
        results.put("poi_tips", poi_tips); // TODO: add json support

        element = this.extract_element(html, FIELDS.get("poi_gmap"));
        String poi_gmap = element != null ? element.attr("src") : "#";
        results.put("poi_gmap", poi_gmap);

        return results;
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
