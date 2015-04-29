#!/usr/bin/env bash

# start hbase
/opt/hbase/bin/start-hbase.sh

# start elasticsearch
nohup /opt/es/bin/elasticsearch > /dev/null &
nohup /opt/kibana/bin/kibana > /dev/null &
