package com.example.kafkafraudapi.controller;

import com.example.kafkafraudapi.entity.Transaction;
import com.example.kafkafraudapi.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public List<Transaction> findAll() {
        return transactionService.findAll().stream().limit(100).toList();
    }

    @GetMapping("/mock")
    public Map<String, String> mock(@RequestParam Integer count) {
        transactionService.mock(count);

        return Collections.singletonMap("status", "OK");
    }
}