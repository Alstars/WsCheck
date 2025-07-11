package com.example.wscheck.analytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionDetails {
    private String transactionId;
    private BigDecimal share;
    private Trend transactionType;
    private Long transactionTime;
    private BigDecimal change;
    private BigDecimal profit;

}
