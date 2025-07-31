package com.example.kafkafraudanalytics.service;

import com.example.kafkafraudanalytics.entity.FraudTransaction;
import com.example.kafkafraudanalytics.repository.FraudTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import mysql.fraud.transactions.Envelope;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FraudCheckerService {

    private final FraudTransactionRepository fraudTransactionRepository;

    public FraudCheckerService(FraudTransactionRepository fraudTransactionRepository) {
        this.fraudTransactionRepository = fraudTransactionRepository;
    }

    public void check(Envelope transaction) {
        if (transaction.getAfter().getAmount() < 10000000) {
            return;
        }

        log.info("Fraudulent transaction found! transactionId: {}, amount: {}$", transaction.getAfter().getId(), transaction.getAfter().getAmount());

        FraudTransaction fraudTransaction = FraudTransaction.builder()
                .transactionId(transaction.getAfter().getId())
                .reason("Transaction is over Rp10.000.000")
                .order_id(transaction.getAfter().getOrderId())
                .user_id(transaction.getAfter().getUserId())
                .amount(transaction.getAfter().getAmount())
                .status(FraudTransaction.Status.valueOf(transaction.getAfter().getStatus()))
                .build();

        fraudTransactionRepository.save(fraudTransaction);
    }
}