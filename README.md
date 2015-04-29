POI search engine from scratch
==============================

A toy poi search engine based on Nutch and Elasticsearch

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


(Optional) Elasticsearch plugin
-------------------------------

1. Head - a web front end for browsing and interacting with an Elastic Search cluster
        bin/plugin --install mobz/elasticsearch-head

2. Bigdesk - to see how your Elasticsearch cluster is doing
        bin/plugin -install lukas-vlcek/bigdesk


References
----------

* [Simple Vagrant Tutorial](http://twang2218.github.io/tutorial/openstack/vagrant.html)
