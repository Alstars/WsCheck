package com.example.wscheck.ws;

//import com.example.wscheck.util.OkxCandleService;
import com.example.wscheck.config.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
public class PrivateWebSocketClient extends WebSocketClient {

    private StandardWebSocketClient webSocketClient;
    private final ObjectMapper objectMapper;
    private final Config config;
    private boolean isConnected = false;
//    private OkxCandleService okxCandleService;

    @Autowired
    public PrivateWebSocketClient(Config config) {
        super(URI.create(config.getWs().getBusinessUrl()));
        this.objectMapper = new ObjectMapper();
        this.config = config;
    }

    public void ensureConnected() {
        if (!isConnected || isClosed()) {
            log.info("Attempting to connect WebSocket...");
            connect();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        isConnected = true;
        log.info("WebSocket connection established");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        isConnected = false;
        log.warn("WebSocket connection closed: {}", reason);
        scheduleReconnect();
    }

    private void scheduleReconnect() {
        try {
            Thread.sleep(5000); // 5 секунд задержки перед переподключением
            ensureConnected();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    @Override
    public void onMessage(String message) {
        try {
            Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
            log.info("Received private message: {}", messageMap);
//            if (message.contains("channel")){
//                OkxCandle candle = okxCandleService.parseCandleMessage(message);
//            }
        } catch (JsonProcessingException e) {
            log.error("Error parsing private WebSocket message", e);
        }
    }

    @Override
    public void onError(Exception ex) {
        log.error("Private WebSocket error", ex);
    }

    public void subscribeToCandles(String instrumentId, String channel) {
        try {
            String subscribeMessage = objectMapper.writeValueAsString(Map.of(
                    "op", "subscribe",
                    "args", new Object[]{Map.of(
                            "channel", channel,  // Изменено с "candle1m" на "candle"
                            "instId", instrumentId
                    )}
            ));
            this.send(subscribeMessage);
            log.info("Subscribed to candles: {} {}", instrumentId, channel);
        } catch (JsonProcessingException e) {
            log.error("Error creating candle subscribe message", e);
            throw new RuntimeException("Failed to subscribe to candles", e);
        }
    }

}