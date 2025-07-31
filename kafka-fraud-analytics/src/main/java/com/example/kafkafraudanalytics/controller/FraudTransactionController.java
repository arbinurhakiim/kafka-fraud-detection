package com.example.kafkafraudanalytics.controller;

import com.example.kafkafraudanalytics.entity.FraudTransaction;
import com.example.kafkafraudanalytics.service.FraudRESTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions/fraud")
public class FraudTransactionController {

    private final FraudRESTService fraudRESTService;

    public FraudTransactionController(FraudRESTService fraudRESTService) {
        this.fraudRESTService = fraudRESTService;
    }

    @GetMapping("/")
    public List<FraudTransaction> findAll() {
        return fraudRESTService.findAll();
    }
}