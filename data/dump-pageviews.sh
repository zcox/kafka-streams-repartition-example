#!/bin/bash

"$KAFKA_HOME/bin/kafka-console-consumer.sh" \
  --bootstrap-server localhost:9092 \
  --topic pageviews \
  --from-beginning \
  --property print.key=true \
  --property key.separator="|" \
  --timeout-ms 1000
