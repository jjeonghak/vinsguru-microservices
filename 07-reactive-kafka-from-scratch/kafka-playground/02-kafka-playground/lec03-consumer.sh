#!/bin/bash

# to consume message
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic

# to consume from beginning
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic --from-beginning

