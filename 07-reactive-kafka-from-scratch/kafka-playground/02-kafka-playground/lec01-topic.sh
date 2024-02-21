#!/bin/bash

# create a kafka topic called topic
# we assume that directory which contains 'kafka-topics.sh' is included in the PATH
kafka-topics.sh \
    --bootstrap-server localhost:9092 \
    --topic topic \
    --create \
    --partitions 4 \
    --replication-factor 3 \
    --config min.insync.replicas=2

# describe a topic
kafka-topics.sh --bootstrap-server localhost:9092 --topic topic --describe

# delete a topic
kafka-topics.sh --bootstrap-server localhost:9092 --topic topic --delete

# set a topic time out
kafka-topics.sh --bootstrap-server localhost:9092 --topic topic --timeout 50

# list all topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

