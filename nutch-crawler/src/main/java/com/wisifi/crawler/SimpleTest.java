package com.wisifi.crawler;

import com.google.gson.Gson;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.search.SearchHit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Created by fwang on 29/4/15.
 *
 * borrow from: http://qindongliang.iteye.com/blog/2192976
 */
public class SimpleTest {

    // es client
    public static Client client=null;

    public static void indexOne()throws Exception{

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user","kimchy");
        data.put("postDate",new Date());
        data.put("message","trying out Elasticsearch");

        // parse to json format
        Gson g=new Gson();
        String json=g.toJson(data);

        // put into es
        IndexResponse ir=client.prepareIndex("test_db", "table", "1").setSource(json).execute().actionGet()               ;

        String index_name=ir.getIndex();
        String index_type=ir.getType();
        String docid=ir.getId();
        long version=ir.getVersion();


        System.out.println("索引名： "+index_name+"  ");
        System.out.println("索引类型： "+index_type+"  ");
        System.out.println("docid： "+docid+"  ");
        System.out.println("版本号： "+version+"  ");


        System.out.println("连接成功！");

    }

    public static void getone()throws Exception{
        GetResponse response = client.prepareGet("test_db", "table", "1")
                .execute()
                .actionGet();
        if(!response.isExists()){
            System.out.println("数据不存在！ ");
            return;
        }
        Map<String, Object> source = response.getSource();
        for(Entry<String, Object> eo : source.entrySet()){
            System.out.println(eo.getKey()+"  "+eo.getValue());
        }

        Map<String, GetField> fields = response.getFields();
        if(fields!=null){
            for(Entry<String, GetField> s : fields.entrySet()){
                System.out.println(s.getKey());
            }

        }else{
            System.out.println("fields is null;");
        }
    }

    public static void updatedoc()throws Exception{
        UpdateRequest ur=new UpdateRequest();
        ur.index("test_db");
        ur.type("table");
        ur.id("1");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user","更新的用户");
        data.put("message","我也要更新了呀");
        ur.doc(data);
        client.update(ur);
        System.out.println("更新成功！");
    }

    public static void indexdata()throws Exception{

        BulkRequestBuilder bulk=client.prepareBulk();

        XContentBuilder doc= XContentFactory.jsonBuilder()
                .startObject()
                .field("title","中国")
                .field("price",205.65)
                .field("type",2)
                .field("status",true)
                .field("address", "河南洛阳")
                .field("createDate", new Date()).endObject();
        //collection为索引库名，类似一个数据库，索引名为core，类似一个表
//       client.prepareIndex("collection1", "core1").setSource(doc).execute().actionGet();

        //批处理添加
        bulk.add(client.prepareIndex("nutch", "entry").setSource(doc));

        doc=XContentFactory.jsonBuilder()
                .startObject()
                .field("title","标题")
                .field("price",251.65)
                .field("type",1)
                .field("status",true)
                .field("address", "美国东部")
                .field("createDate", new Date()).endObject();
        //collection为索引库名，类似一个数据库，索引名为core，类似一个表
//      client.prepareIndex("collection1", "core1").setSource(doc).execute().actionGet();
        //批处理添加
        bulk.add(client.prepareIndex("nutch", "entry").setSource(doc));

        doc=XContentFactory.jsonBuilder()
                .startObject()
                .field("title","elasticsearch是一个搜索引擎")
                .field("price",25.65)
                .field("type",3)
                .field("status",true)
                .field("address", "china")
                .field("createDate", new Date()).endObject();
        //collection为索引库名，类似一个数据库，索引名为core，类似一个表
        //client.prepareIndex("collection1", "core1").setSource(doc).execute().actionGet();
        //批处理添加
        bulk.add(client.prepareIndex("nutch", "entry").setSource(doc));


        //发一次请求，提交所有数据
        BulkResponse bulkResponse = bulk.execute().actionGet();
        if (!bulkResponse.hasFailures()) {
            System.out.println("创建索引success!");
        } else {
            System.out.println("创建索引异常:"+bulkResponse.buildFailureMessage());
        }


    }

    public static void querySimple()throws Exception{

        SearchResponse sp = client.prepareSearch("nutch").execute().actionGet();
        for(SearchHit hits : sp.getHits().getHits()){
            String sourceAsString = hits.sourceAsString();//以字符串方式打印
            System.out.println(sourceAsString);
        }
    }

    public static void main(String[] args)throws Exception {
        SimpleTest.init();

//        SimpleTest.indexOne();
//        SimpleTest.getone();
//        SimpleTest.updatedoc();

        SimpleTest.indexdata();
        SimpleTest.querySimple();
        SimpleTest.stop();
    }

    public static void init() {
        if (client == null) {
            // connect to single machine
            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "numb3r3").build();
            client=new TransportClient(settings).
                    addTransportAddress(new InetSocketTransportAddress("192.168.33.10", 9300));

        }
    }
    public static void stop() {
        client.close(); //释放资源
    }
}
