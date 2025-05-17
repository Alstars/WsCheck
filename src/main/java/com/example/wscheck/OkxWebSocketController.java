package com.example.wscheck;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/okx/ws")
@RequiredArgsConstructor
public class OkxWebSocketController {

    private final OkxWebSocketService okxWebSocketService;
    private final OkxPrivateWebSocketClient okxPrivateWebSocketClient;

    @PostMapping("/connect")
    public String connectAll() {
        okxPrivateWebSocketClient.ensureConnected();
        return "WebSocket connections initiated";
    }

    @PostMapping("/disconnect")
    public String disconnectAll() {
        okxPrivateWebSocketClient.close();
        return "WebSocket connections closed";
    }

    @GetMapping("/status")
    public Map<String, Boolean> getConnectionStatus() {
        return Map.of(
                "privateConnection", okxPrivateWebSocketClient.isOpen()
        );
    }

    @PostMapping("/subscribe/candles/{instrumentId}")
    public String subscribeToCandles(@PathVariable String instrumentId, @RequestParam(defaultValue = "candle1m") String channel) {
        okxWebSocketService.subscribeToCandles(instrumentId, channel);
        return "Subscribed to candles for " + instrumentId;
    }
}
