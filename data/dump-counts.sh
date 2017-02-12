#!/bin/bash

"$KAFKA_HOME/bin/kafka-console-consumer.sh" \
  --bootstrap-server localhost:9092 \
  --topic pageview-counts-by-url \
  --from-beginning \
  --property print.key=true \
  --property key.separator="|" \
  --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer \
  --timeout-ms 1000
