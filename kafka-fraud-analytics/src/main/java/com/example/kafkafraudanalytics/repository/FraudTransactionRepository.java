package com.example.kafkafraudanalytics.repository;

import com.example.kafkafraudanalytics.entity.FraudTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudTransactionRepository extends JpaRepository<FraudTransaction, Long> {
    //
}