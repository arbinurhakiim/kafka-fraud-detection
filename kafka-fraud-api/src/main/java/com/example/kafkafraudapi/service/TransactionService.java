package com.example.kafkafraudapi.service;

import com.example.kafkafraudapi.entity.Transaction;
import com.example.kafkafraudapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public void mock(Integer count) {
        List<Transaction> mockTransactions = new ArrayList<>();
        List<Transaction.Status> statuses = List.of(Transaction.Status.values());

        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setOrder_id(ThreadLocalRandom.current().nextLong(1, 10000));
            transaction.setUser_id(ThreadLocalRandom.current().nextLong(1, 1000));
            transaction.setAmount(ThreadLocalRandom.current().nextInt(50, 121) * 100_000);
            transaction.setStatus(statuses.get(ThreadLocalRandom.current().nextInt(statuses.size())));

            mockTransactions.add(transaction);
        }

        transactionRepository.saveAll(mockTransactions);
    }
}