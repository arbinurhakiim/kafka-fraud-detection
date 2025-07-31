package com.example.kafkafraudanalytics.service;

import com.example.kafkafraudanalytics.entity.FraudTransaction;
import com.example.kafkafraudanalytics.repository.FraudTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FraudRESTService {

    private final FraudTransactionRepository transactionRepository;

    public FraudRESTService(FraudTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<FraudTransaction> findAll() {
        return transactionRepository.findAll();
    }
}