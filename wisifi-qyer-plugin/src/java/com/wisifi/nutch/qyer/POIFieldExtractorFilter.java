package com.wisifi.nutch.qyer;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.parse.HTMLMetaTags;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.ParseFilter;
import org.apache.nutch.storage.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DocumentFragment;

import java.nio.ByteBuffer;
import java.util.*;



/**
 * parse out the poi title, categories from the HTML page
 *
 * @author fwang on 01/05/2015
 */
public class POIFieldExtractorFilter implements ParseFilter {
    public static final Logger LOG = LoggerFactory.getLogger("com.wisifi.nutch.qyer.parsefilter");

    private static final List<String> htmlMimeTypes = Arrays.asList(new String[]{"text/html", "application/xhtml+xml"});

    // Configuration
    private Configuration configuration;

    private FieldExtractor fieldExtractor = null;

    public POIFieldExtractorFilter() {
        init();
    }

    private void init() {
        fieldExtractor = new FieldExtractor();
    }



    @Override
    public Parse filter(String url, WebPage page, Parse parse, HTMLMetaTags metaTags, DocumentFragment doc) {
        LOG.info("QYER ParseFilter plugin");

        if (page.equals(null)) {
            LOG.error("WebPage is null");
            return parse;
        }

        ByteBuffer buffer = page.getContent();
        if (buffer == null) {
            LOG.error("WebPage content is null");
            return parse;
        }

        if (!URLPattern.is_poi(url)) { // this page is not a POI page
            return parse;
        }

        page.putToMetadata(new Utf8("type"), ByteBuffer.wrap("poi".getBytes()));

        byte[] rawContent = buffer.array();

        if (htmlMimeTypes.contains(page.getContentType().toString())) {
            // Reader rawContentReader = new InputStreamReader(new ByteArrayInputStream(rawContent), getCharsetFromContent(rawContent));
            String html = new String(rawContent);
            for (Map.Entry<String, Object> field : fieldExtractor.extractPOIFields(url, html).entrySet()) {
                // TODO: handle the extracted object (e.g., array)

                page.putToMetadata(new Utf8(field.getKey()),
                        ByteBuffer.wrap(field.getValue().toString().getBytes()));


            }
        }

        return parse;
    }


    @Override
    public void setConf(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConf() {
        return this.configuration;
    }

    @Override
    public Collection<WebPage.Field> getFields() {
        return Collections.singleton(WebPage.Field.METADATA);
    }


}
