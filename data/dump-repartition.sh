#!/bin/bash

"$KAFKA_HOME/bin/kafka-console-consumer.sh" \
  --bootstrap-server localhost:9092 \
  --topic pageview-analytics-pageview-counts-by-url-repartition \
  --from-beginning \
  --property print.key=true \
  --property key.separator="|" \
  --timeout-ms 1000
