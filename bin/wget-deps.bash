#!/bin/bash
# wget-deps.bash - download dependencies
ROOTDIR="/vagrant"

. `dirname $0`/setenv.bash


ANT_URL="http://apache.communilink.net//ant/binaries/$ANT_ARCHIVE"
HBASE_URL="http://apache.communilink.net/hbase/hbase-$HBASE_VER/$HBASE_ARCHIVE"
NUTCH_URL="https://archive.apache.org/dist/nutch/$NUTCH_VER/$NUTCH_ARCHIVE"
ES_URL="https://download.elastic.co/elasticsearch/elasticsearch/$ES_ARCHIVE"
KIBANA_URL="https://download.elastic.co/kibana/kibana/$KIBANA_ARCHIVE"
cd $ROOTDIR/downloads


if [ ! -f $ANT_ARCHIVE ]; then
    wget $ANT_URL
fi

if [ ! -f $HBASE_ARCHIVE ]; then
    wget $HBASE_URL
fi

if [ ! -f $NUTCH_ARCHIVE ]; then
    wget $NUTCH_URL
fi

if [ ! -f $ES_ARCHIVE ]; then
    wget $ES_URL
fi

if [ ! -f $KIBANA_ARCHIVE ]; then
    wget $KIBANA_URL
fi
