package com.wisifi.nutch.qyer;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.storage.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by fwang on 1/5/15.
 */
public class POIIndexingFilter implements IndexingFilter {
    public static final Logger LOG = LoggerFactory.getLogger("com.wisifi.nutch.qyer.poiindexingfilter");

    private static Collection<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();
    private Configuration configuration;

    @Override
    public NutchDocument filter(NutchDocument doc, String url, WebPage page) throws IndexingException {
        //get page meta
        Map<Utf8, ByteBuffer> metadata = page.getMetadata();

        //Add to index metadata
        Iterator entries = metadata.entrySet().iterator();

        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            Object key = thisEntry.getKey();
            Object value = thisEntry.getValue();
            String cname = value.getClass().getName();
//            if(cname.equals("java.nio.ByteBuffer")) {
//            }
            ByteBuffer buff = (ByteBuffer) value;
            String val = new String(buff.array());
            Utf8 keyname = (Utf8) key;
            doc.add(keyname.toString(), val);
        }
        return doc;
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
        return FIELDS;
    }
}
