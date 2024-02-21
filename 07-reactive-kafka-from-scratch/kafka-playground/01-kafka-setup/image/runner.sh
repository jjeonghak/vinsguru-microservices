#!/bin/bash

# If env variable not set, generate random one
# Large organizations might have multiple kafka clusters. Each cluster is expected to have an ID
clusterId=${KAFKA_CLUSTER_ID:-$(./bin/kafka-storage.sh random-uuid)}
echo "Kafka Cluster Id: ${clusterId}"

# For the first time, format the storage. It would create couple of files
# If it is already formatted, it would be ignored.

echo "Formatting storage"
kafka-storage.sh format -t $clusterId -c kafka/config/kraft/server.properties

# Finally start the kafka server

echo "Start Kafka"

exec kafka-server-start.sh kafka/config/kraft/server.properties
