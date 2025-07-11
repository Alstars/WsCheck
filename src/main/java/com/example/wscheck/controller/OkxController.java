package com.example.wscheck.controller;

import com.example.wscheck.model.OkxInstrumentResponse;
import com.example.wscheck.model.OkxServerTimeResponse;
import com.example.wscheck.model.OkxTickerResponse;
import com.example.wscheck.rest.OkxRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/okx")
@RequiredArgsConstructor
public class OkxController {

    private final OkxRestClient okxRestClient;

    @GetMapping("/instruments")
    public OkxInstrumentResponse getInstruments(
            @RequestParam String instType,
            @RequestParam(required = false) String uly,
            @RequestParam(required = false) String instId) {
        return okxRestClient.getInstruments(instType, uly, instId);
    }

    @GetMapping("/ticker")
    public OkxTickerResponse getTicker(@RequestParam String instId) {
        return okxRestClient.getTicker(instId);
    }

    @GetMapping("/time")
    public OkxServerTimeResponse getServerTime() {
        return okxRestClient.getServerTime();
    }
}