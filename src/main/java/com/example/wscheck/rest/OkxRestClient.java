package com.example.wscheck.rest;

import com.example.wscheck.model.OkxInstrumentResponse;
import com.example.wscheck.model.OkxServerTimeResponse;
import com.example.wscheck.model.OkxTickerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OkxRestClient {

    private final RestTemplate okxRestTemplate;
    private final String okxBaseUrl;

    public OkxInstrumentResponse getInstruments(String instType, String uly, String instId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(okxBaseUrl)
                .path("/api/v5/public/instruments")
                .queryParam("instType", instType)
                .queryParamIfPresent("uly", Optional.ofNullable(uly))
                .queryParamIfPresent("instId", Optional.ofNullable(instId))
                .build()
                .toUri();

        log.info("Requesting instruments from OKX: {}", uri);
        OkxInstrumentResponse response = okxRestTemplate.getForObject(uri, OkxInstrumentResponse.class);
        log.info("Received instruments response: {}", response);
        return response;
    }

    public OkxTickerResponse getTicker(String instId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(okxBaseUrl)
                .path("/api/v5/market/ticker")
                .queryParam("instId", instId)
                .build()
                .toUri();

        log.info("Requesting ticker from OKX for instrument: {}", instId);
        OkxTickerResponse response = okxRestTemplate.getForObject(uri, OkxTickerResponse.class);
        log.info("Received ticker response: {}", response);
        return response;
    }

    public OkxServerTimeResponse getServerTime() {
        URI uri = UriComponentsBuilder.fromHttpUrl(okxBaseUrl)
                .path("/api/v5/public/time")
                .build()
                .toUri();

        log.info("Requesting server time from OKX");
        OkxServerTimeResponse response = okxRestTemplate.getForObject(uri, OkxServerTimeResponse.class);
        log.info("Received server time: {}", response);
        return response;
    }
}
