POI search engine from scratch
==============================


A toy poi search engine based on Nutch and Elasticsearch

        HBASE_VER="0.90.4"
        NUTCH_VER="2.2.1"
        ES_VER="1.5.2"

**Outputs:** The Qyer Nutch Plugin
----------------------------------

**Goal**: to extract specific information about the POI from crawled web page

The extracted fields (see "FieldExtractor.java"):

        * **POI title**: e.g., "Helsingin Tuomiokirkko 赫尔辛基大教堂"

        * **POI star rating**: e.g., 8.9

        * **POI description**

        * **POI tips**: include the address, categories and so on

        * **POI googl map url**: the google map of this location. we can also get the geographic coordinates (lat, lon) [TODO].

**Issues**

1. the html is parsed using [Jsoup](http://jsoup.org/), which would be very slow. The xpath method would be preferred.

2. the nutch version is 2.2.1 (latest 2.3).



Install
-------

1. Set up vagrant

        vagrant up --provision
        vagrant ssh
        cd /vagrant

2. Download and install ant, nutch and es

        bin/wget-deps.bash
        bin/setup.bash

3. Build Nutch

        bin/build-nutch.bash

>> **Note:**
>> 1) config the JAVA_HOME in the $HBASE_HOME/conf/hbase-config.sh
>> 2) check /etc/hosts

(Optional) Elasticsearch plugin
-------------------------------

1. Head - a web front end for browsing and interacting with an Elastic Search cluster

        bin/plugin --install mobz/elasticsearch-head

    access http://localhost:9200/_plugin/head/

2. Bigdesk - to see how your Elasticsearch cluster is doing

        bin/plugin -install lukas-vlcek/bigdesk

    access http://localhost:9200/_plugin/bigdesk/


(Optional) Third part tools
---------------------------

1. **[Kibana](https://github.com/elastic/kibana)** - An open source browser based analytics and search dashboard for Elasticsearch


Sequence of operations
---------------------

1. **Inject** - populates CrawlDB from seed urls

2. **Generate** - select URLS to fetch in segment

3. **Fetch** - fetch URL from segment

4. **Parse** - parse content (text + meta data)

5. **UpdateDB** - update CrawlDB (new URLs, new status)

6. **InvertLinks** - build web graph

7. **ESIndex** - send doc into ES

Database schema
---------------

* **index**: "poi"
* **type**: "qyer" # sources, e.g., place.qyer.com


References
----------

* [Simple Vagrant Tutorial](http://twang2218.github.io/tutorial/openstack/vagrant.html)

* [Web crawling with nutch](http://events.linuxfoundation.org/sites/events/files/slides/aceu2014-snagel-web-crawling-nutch.pdf)

* [Nutch Elasticsearch guidline (old version)](https://github.com/duffj/nutch-elasticsearch)

* [Nutch 2.3 + ElasticSearch 1.4 + HBase 0.94 Setup gist](https://gist.github.com/xrstf/b48a970098a8e76943b9)

* [Nutch Elasticsearch Mongo](http://www.aossama.com/search-engine-with-apache-nutch-mongodb-and-elasticsearch/)

* [How to write nutch plugin](http://wiki.apache.org/nutch/WritingPluginExample)

* [Nutch crawling workflow](http://www.cnblogs.com/huligong1234/p/3515214.html)
