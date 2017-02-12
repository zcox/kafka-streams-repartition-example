package ch.theza

import java.util.Properties
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{StreamsConfig, KafkaStreams}
import org.apache.kafka.streams.kstream.KStreamBuilder
import org.apache.kafka.clients.consumer.ConsumerConfig

object PageviewAnalyticsMain extends App {

  val applicationId = "pageview-analytics"
  val pageviewsInputTopic = "pageviews"
  val pageviewCountsByUrlState = "pageview-counts-by-url"
  val pageviewCountsByUrlOutputTopic = "pageview-counts-by-url"

  val kafkaBootstrapServers = "localhost:9092"
  val zookeeperConnect = "localhost:2181"

  val props = new Properties
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId)
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers)
  props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, zookeeperConnect)
  props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
  props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
  props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "5000") //default 30sec is too long to wait for state updates in this example
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  val streamsConfig = new StreamsConfig(props)

  val builder = new KStreamBuilder
  builder
    .stream(pageviewsInputTopic)
    .selectKey((key: String, value: String) => PageviewFormat.fromMessageValue(value).url)
    .groupByKey()
    .count(pageviewCountsByUrlState)
    .to(Serdes.String(), Serdes.Long(), pageviewCountsByUrlOutputTopic)

  val streams = new KafkaStreams(builder, streamsConfig)
  streams.start()
}
