package com.example.kafkafraudanalytics.stream;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.slf4j.Slf4j;
import mysql.fraud.transactions.Envelope;
import mysql.fraud.transactions.Key;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Duration;
import java.util.Collections;

@Slf4j
@Configuration
@EnableKafkaStreams
public class TransactionPerMinuteStream {

    private static final String INPUT_TOPIC = "mysql.fraud.transactions";
    private static final String OUTPUT_TOPIC = "transactions-per-minute";

    @Bean
    public KStream<Windowed<String>, Long> transactionPerMinuteCounterStream(StreamsBuilder builder) {
        KStream<Key, Envelope> inputStream = builder.stream(INPUT_TOPIC);

        final Serde<Envelope> envelopeSerde = new SpecificAvroSerde<>();
        envelopeSerde.configure(Collections.singletonMap("schema.registry.url", "http://localhost:8091"), false);

        KStream<Windowed<String>, Long> transactionCounts = inputStream
                .filter((key, envelope) -> envelope.getAfter() != null)
                .groupBy((key, value) -> "all-transactions", Grouped.with(Serdes.String(), envelopeSerde))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
                .count(Materialized.as("transactions-per-minute-store"))
                .toStream();

        transactionCounts.peek((windowedKey, count) -> log.info("Window: {} to {}, Count: {}", windowedKey.window().startTime(), windowedKey.window().endTime(), count));

        transactionCounts.to(OUTPUT_TOPIC, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.Long()));

        return transactionCounts;
    }
}