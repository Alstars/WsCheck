package com.example.wscheck.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OkxWsCandleMessage {
    @JsonProperty("arg")
    private CandleArgs args;

    @JsonProperty("data")
    private List<List<Object>> candleData;

    @Data
    public static class CandleArgs {
        @JsonProperty("channel")
        private String channel;

        @JsonProperty("instId")
        private String instrumentId;
    }
}