package com.example.wscheck.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OkxCandle {
    @JsonProperty("instId")
    private String instrumentId;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("ts")
    private String timestamp; // Временная метка в формате "1597026383085"

    @JsonProperty("o")
    private BigDecimal open; // Цена открытия

    @JsonProperty("h")
    private BigDecimal high; // Максимальная цена

    @JsonProperty("l")
    private BigDecimal low; // Минимальная цена

    @JsonProperty("c")
    private BigDecimal close; // Цена закрытия

    @JsonProperty("vol")
    private BigDecimal volume; // Объем в базовой валюте

    @JsonProperty("volCcy")
    private BigDecimal volumeCcy; // Объем в валюте котировки

    @JsonProperty("volCcyQuote")
    private BigDecimal volCcyQuote; // Объем в валюте котировки

    @JsonProperty("confirm")
    private String confirm; // Состояние свечи (0 - не подтверждена, 1 - подтверждена)


//    @JsonProperty("ts")
//    public void setTimestampFromMillis(long millis) {
//        this.timestamp = LocalDateTime.ofInstant(
//                Instant.ofEpochMilli(millis),
//                ZoneId.systemDefault()
//        );
//    }
//
//    @JsonProperty("ts")
//    public long getTimestampAsMillis() {
//        return timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//    }
}
