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


References
----------

* [Simple Vagrant Tutorial](http://twang2218.github.io/tutorial/openstack/vagrant.html)
