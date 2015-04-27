#!/bin/bash
# wget-deps.bash - download dependencies
ROOTDIR="/vagrant"

. `dirname $0`/setenv.bash


ANT_URL="http://apache.communilink.net//ant/binaries/$ANT_ARCHIVE"
HBASE_URL="http://archive.apache.org/dist/hbase/hbase-$HBASE_VER/$HBASE_ARCHIVE"
NUTCH_URL="http://apache.communilink.net/nutch/$NUTCH_VER/$NUTCH_ARCHIVE"
ES_URL="https://download.elastic.co/elasticsearch/elasticsearch/$ES_ARCHIVE"
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
