package com.example.wscheck.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OkxInstrumentResponse {
    private String code;
    private String msg;
    private List<Instrument> data;

    @Data
    public static class Instrument {
        private String instType;
        private String instId;
        private String uly;
        private String category;
        private String baseCcy;
        private String quoteCcy;
        private String settleCcy;
        private String ctVal;
        private String ctMult;
        private String ctValCcy;
        private String optType;
        private String stk;
        private String listTime;
        private String expTime;
        private String lever;
        private String tickSz;
        private String lotSz;
        private String minSz;
        private String ctType;
        private String alias;
        private String state;
    }
}

