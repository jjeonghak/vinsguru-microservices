#!/bin/bash

# recive messages with offset info and time stamp
kafka-console-consumer.sh \
    --bootstrap-server localhost:9092 \
    --topic topic \
    --property print.offset=true \
    --property print.timestamp=true

