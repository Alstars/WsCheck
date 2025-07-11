package com.example.wscheck.model;

import lombok.Data;

import java.util.List;

@Data
public class OkxTickerResponse {
    private String code;
    private String msg;
    private List<Ticker> data;

    @Data
    public static class Ticker {
        private String instType;
        private String instId;
        private String last;
        private String lastSz;
        private String askPx;
        private String askSz;
        private String bidPx;
        private String bidSz;
        private String open24h;
        private String high24h;
        private String low24h;
        private String volCcy24h;
        private String vol24h;
        private String sodUtc0;
        private String sodUtc8;
        private String ts;
    }
}
