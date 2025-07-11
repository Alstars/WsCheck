//package com.example.wscheck.util;
//
//import com.example.wscheck.Model.OkxWsCandleMessage;
//import com.example.wscheck.Model.OkxCandle;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class OkxCandleService {
//    private final ObjectMapper objectMapper;
//
//    public OkxCandle parseCandleMessage(String message) throws JsonProcessingException {
//        OkxWsCandleMessage wsCandleMessagecandleMessage = objectMapper.readValue(message, OkxWsCandleMessage.class);
//
//        if (wsCandleMessagecandleMessage.getCandleData() == null || wsCandleMessagecandleMessage.getCandleData().isEmpty()) {
//            throw new IllegalArgumentException("No candle data in message");
//        }
//
//        List<Object> data = wsCandleMessagecandleMessage.getCandleData().get(0);
//        OkxCandle candle = new OkxCandle();
//        candle.setInstrumentId(wsCandleMessagecandleMessage.getArgs().getInstrumentId());
//        candle.setChannel(wsCandleMessagecandleMessage.getArgs().getChannel());
//
//        candle.setTimestampFromMillis(Long.parseLong(data.get(0).toString()));
//        candle.setOpen(new BigDecimal(data.get(1).toString()));
//        candle.setHigh(new BigDecimal(data.get(2).toString()));
//        candle.setLow(new BigDecimal(data.get(3).toString()));
//        candle.setClose(new BigDecimal(data.get(4).toString()));
//        candle.setVolume(new BigDecimal(data.get(5).toString()));
//        candle.setVolumeCurrency(new BigDecimal(data.get(6).toString()));
//        candle.setVolumeCurrencyQuote(new BigDecimal(data.get(7).toString()));
//        candle.setConfirm(Long.parseLong(data.get(8).toString()));
//
//        return candle;
//    }
//}
