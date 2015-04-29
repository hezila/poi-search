#!/bin/bash
# Build nutch on precise64 using apache ant with OpenJDK-1.7.0.
# Prerequisites:
# Must have openjdk RPM installed, and nutch and ant extracted into /opt
ts=`date +%Y%m%d%H%M%S`

. `dirname $0`/setenv.bash

if [ ! $JAVA_HOME ]; then
    export JAVA_HOME="/usr/lib/jvm/java-7-openjdk-amd64"
fi

if [ ! $NUTCH_HOME ]; then
    NUTCH_HOME="/opt/nutch"
    echo "!!! NUTCH_HOME not set, defaulting to $NUTCH_HOME"
fi

if [ ! $ANT_HOME ]; then
    ANT_HOME="/opt/ant"
    echo "!!! ANT_HOME not set, defaulting to $ANT_HOME"
fi

if [ ! $HBASE_HOME ]; then
    HBASE_HOME="/opt/hbase"
    echo "!!! HBASE_HOME not set, default to $HBASE_HOME"
fi

if [ ! $ES_HOME ]; then
    ES_HOME="/opt/es"
    echo "!!! ES_HOME not set, default to $ES_HOME"
fi

cd $NUTCH_HOME
if [ -d runtime ]; then
    echo "Backing up previous runtime directory"
    mv runtime runtime.bak-$ts
fi


# use the custom config file.
cp /$SRCROOT/conf/nutch/ivy.xml ivy/ivy.xml
cp /$SRCROOT/conf/nutch/gora.properties conf/gora.properties
cp /$SRCROOT/conf/nutch/nutch-site.xml conf/nutch-site.xml
cp /$SRCROOT/conf/hbase/hbase-site.xml $HBASE_HOME/conf/hbase-site.xml
cp /$SRCROOT/conf/es/elasticsearch.yml $ES_HOME/config/elasticsearch.yml

cp /$SRCROOT/patches/ElasticWriter.java $NUTCH_HOME/src/java/org/apache/nutch/indexer/elastic/ElasticWriter.java
# build nutch
$ANT_HOME/bin/ant runtime

# Install the custom config file.
cd runtime/local
mkdir urls && cp /$SRCROOT/conf/urls.txt urls

echo "Build complete, now run the crawler (see the README for details)."
