package com.example.wscheck.analytics;


import com.example.wscheck.model.OkxCandle;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Slf4j
public class MaxProfitCalculator {

    List<TransactionDetails> bullProfit = new ArrayList<>();
    List<TransactionDetails> bearProfit = new ArrayList<>();
    BigDecimal capitalValue = BigDecimal.ONE;
    BigDecimal highestBullChange;
    BigDecimal highestBearChange;
    List<OkxCandle> candles = new ArrayList<>();
    Long transactionCount = 0L;

    BigDecimal highest = BigDecimal.ZERO;
    BigDecimal lowest = BigDecimal.ZERO;
    Trend trend = Trend.UNDEFINED;

    public String guaranteedProfit(List<OkxCandle> candles, String timeframe, BigDecimal fee) {

        invalidateVariables();
        OkxCandle lastCandle = null;

        log.info("received new batch,computing");
        ArrayList<OkxCandle> sortedCandles = candles.stream().sorted(Comparator.comparing(OkxCandle::getOpen)).collect(Collectors.toCollection(ArrayList::new));

        ListIterator<OkxCandle> iterator = sortedCandles.listIterator();

        while (iterator.hasNext()) {
            OkxCandle candle = iterator.next();

            if (!iterator.hasNext()) {
                lastCandle = candle;
            }
            BigDecimal difference = candle.getOpen().subtract(candle.getClose());
            if (difference.equals(BigDecimal.ZERO)) {
                break;
            }
            if (lowest == null || highest == null) {
                determineTrend(difference, candle);
            }
            if (isBullCandle(candle) && trend.equals(Trend.UPTREND)) {
                highest = candle.getClose();
                break;
            } else if (!isBullCandle(candle) && trend.equals(Trend.DOWNTREND)) {
                lowest = candle.getClose();
                break;
            } else if (isBullCandle(candle) && trend.equals(Trend.DOWNTREND)) {
                if (ifProfitable(candle.getClose().divide(lowest, RoundingMode.HALF_UP).subtract(BigDecimal.ONE), fee)) {
                    tryTransaction();
                    trend = Trend.UPTREND;
                    highest = candle.getClose();
                }
            } else if (!isBullCandle(candle) && trend.equals(Trend.UPTREND)) {
                if (ifProfitable(candle.getClose().divide(lowest, RoundingMode.HALF_UP).subtract(BigDecimal.ONE), fee)) {
                    tryTransaction();
                    trend = Trend.DOWNTREND;
                    lowest = candle.getClose();
                }
            }
        }
        if (lastCandle != null) {
            if (ifProfitable(lastCandle.getClose(), fee)) {
                tryTransaction();
            }
        }
        return "";
    }


    private Boolean ifProfitable(BigDecimal change, BigDecimal fee) {
        return change.subtract(fee).compareTo(BigDecimal.ZERO) > 0;
    }

    private Boolean isBullCandle(OkxCandle candle) {
        return candle.getOpen().subtract(candle.getClose()).compareTo(BigDecimal.ZERO) > 0;
    }

    private void tryTransaction() {
        BigDecimal change = highest.subtract(lowest).abs().divide(lowest, RoundingMode.HALF_UP);
        BigDecimal profit = highest.subtract(lowest).abs().multiply(capitalValue);
        TransactionDetails transaction = TransactionDetails.builder()
                .transactionType(trend)
                .transactionTime(System.currentTimeMillis())
                .change(change)
                .profit(profit)
                .build();
        capitalValue = capitalValue.multiply(BigDecimal.ONE.add(change));
        transactionCount++;
        if (trend.equals(Trend.UPTREND)) {
            bullProfit.add(transaction);// С учетом увеличения капитала
        } else {
            bearProfit.add(transaction);
        }
    }

    private void invalidateVariables() {
        bullProfit.clear();
        bearProfit.clear();
        capitalValue = BigDecimal.ONE;
        highest = null;
        lowest = null;
        highestBullChange = null;
        highestBearChange = null;
        candles.clear();
        transactionCount = 0L;
    }

    private void determineTrend(BigDecimal difference, OkxCandle candle) {
        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            lowest = candle.getOpen();
            highest = candle.getClose();
            trend = Trend.UPTREND;
        } else {
            lowest = candle.getClose();
            highest = candle.getOpen();
            trend = Trend.DOWNTREND;
        }
    }
}