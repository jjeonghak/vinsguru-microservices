#!/bin/bash

# dry-run
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group name \
    --topic topic \
    --reset-offsets \
    --shift-by 3 \
    --dry-run

# reset offset by shift
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group name \
    --topic topic \
    --reset-offsets \
    --shift-by 3 \
    --execute

# reset offset by duration
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group name \
    --topic topic \
    --reset-offsets \
    --by-duration PT5M \
    --execute

# -- to the beginning
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group name \
    --topic topic \
    --reset-offsets \
    --to-earliest \
    --execute

# -- to the end
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group name \
    --topic topic \
    --reset-offsets \
    --to-latest \
    --execute

# -- to date-time
kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group name \
    --topic topic \
    --reset-offsets \
    --to-datetime 2022-11-28T00:00:00.000 \
    --execute


