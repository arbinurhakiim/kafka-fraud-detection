package com.example.kafkafraudanalytics.consumer;

import com.example.kafkafraudanalytics.service.FraudCheckerService;
import lombok.extern.slf4j.Slf4j;
import mysql.fraud.transactions.Envelope;
import mysql.fraud.transactions.Key;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionConsumer {

    private final FraudCheckerService fraudCheckerService;

    public TransactionConsumer(FraudCheckerService fraudCheckerService) {
        this.fraudCheckerService = fraudCheckerService;
    }

    @KafkaListener(topics = "mysql.fraud.transactions")
    public void checkFraudulentTransaction(@Header(KafkaHeaders.RECEIVED_KEY) Key key, @Payload Envelope transaction) {
        fraudCheckerService.check(transaction);
    }
}