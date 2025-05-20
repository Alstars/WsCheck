package com.example.wscheck;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final PrivateWebSocketClient privateWebSocketClient;

    @PostConstruct
    public void init() {
        log.info("Initializing WebSocket connections...");
        privateWebSocketClient.ensureConnected();
    }

    public void subscribeToCandles(String instrumentId, String channel) {
        Map<String, Object> args = new HashMap<>();
        args.put("instId", instrumentId);
        privateWebSocketClient.subscribeToCandles(instrumentId, channel);
    }

}
