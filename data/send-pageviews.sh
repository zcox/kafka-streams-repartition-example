#!/bin/bash

cat ./pageviews | "$KAFKA_HOME/bin/kafka-console-producer.sh" \
  --broker-list localhost:9092 \
  --topic pageviews \
  --property parse.key=true \
  --property key.separator="|" \
  --request-required-acks 1
