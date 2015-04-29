#!/bin/bash

ANT_VER="1.9.4"
HBASE_VER="0.90.4"
NUTCH_VER="2.2.1"
ES_VER="1.5.2"
KIBANA_VER="4.0.2-linux-x64"

ANT_ARCHIVE="apache-ant-$ANT_VER-bin.tar.gz"
NUTCH_ARCHIVE="apache-nutch-$NUTCH_VER-src.tar.gz"
HBASE_ARCHIVE="hbase-$HBASE_VER.tar.gz"
ES_ARCHIVE="elasticsearch-$ES_VER.tar.gz"
KIBANA_ARCHIVE="kibana-$KIBANA_VER.tar.gz"

ARCHIVES="$ANT_ARCHIVE $HBASE_ARCHIVE $NUTCH_ARCHIVE $ES_VER $KIBANA_VER"

SRCROOT="/vagrant"
APPROOT="/opt"
