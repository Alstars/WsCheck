package com.example.wscheck.analytics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Trend {

    UPTREND("Восходящий тренд", "Цена закрытия выше цены открытия"),

    DOWNTREND("Нисходящий тренд", "Цена закрытия ниже цены открытия"),

    SIDEWAYS("Боковой тренд", "Цена закрытия близка к цене открытия"),

    UNDEFINED("Неопределенный тренд", "Недостаточно данных для определения");

    private final String description;
    private final String condition;

    Trend(String description, String condition) {
        this.description = description;
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

    /**
     * Определяет тренд на основе разницы между ценой открытия и закрытия.
     *
     * @param openPrice  цена открытия
     * @param closePrice цена закрытия
     * @param threshold  порог для определения бокового тренда (в процентах или абсолютных единицах)
     * @return соответствующий тренд
     */
    public static Trend determineTrend(
            BigDecimal openPrice,
            BigDecimal closePrice,
            BigDecimal threshold,
            int scale
    ) {
        if (openPrice.compareTo(BigDecimal.ZERO) <= 0 ||
                closePrice.compareTo(BigDecimal.ZERO) <= 0 ||
                threshold.compareTo(BigDecimal.ZERO) < 0) {
            return UNDEFINED;
        }

        BigDecimal difference = closePrice.subtract(openPrice).abs();
        BigDecimal percentDifference = difference
                .divide(openPrice, scale, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        if (percentDifference.compareTo(threshold) <= 0) {
            return SIDEWAYS;
        } else if (closePrice.compareTo(openPrice) > 0) {
            return UPTREND;
        } else {
            return DOWNTREND;
        }
    }
}