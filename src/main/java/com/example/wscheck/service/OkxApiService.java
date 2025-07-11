package com.example.wscheck.service;

import com.example.wscheck.model.OkxCandle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OkxApiService {

    private static final String OKX_API_BASE_URL = "https://www.okx.com/api/v5/market/candles";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<OkxCandle> getCandles(String instId, String bar, int limit) throws Exception {
        String url = String.format("%s?instId=%s&bar=%s&limit=%d", OKX_API_BASE_URL, instId, bar, limit);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            log.info("Sending request to OKX API: {}", url);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                if (response.getStatusLine().getStatusCode() != 200) {
                    log.error("Error from OKX API: {}", responseBody);
                    throw new RuntimeException("Failed to get candles from OKX API");
                }

                // OKX возвращает данные в формате {"code":"0","msg":"","data":[[...]]}
                var wrapper = objectMapper.readValue(responseBody, new TypeReference<ApiResponse<List<List<String>>>>() {});

                if (!"0".equals(wrapper.getCode())) {
                    log.error("API returned error: code={}, msg={}", wrapper.getCode(), wrapper.getMsg());
                    throw new RuntimeException("API error: " + wrapper.getMsg());
                }

                // Конвертируем List<List<String>> в List<OkxCandle>
                return wrapper.getData().stream()
                        .map(this::parseCandle)
                        .toList();
            }
        }
    }

    private OkxCandle parseCandle(List<String> candleData) {
        OkxCandle candle = new OkxCandle();
        candle.setTimestamp(candleData.get(0));
        candle.setOpen(new BigDecimal(candleData.get(1)));
        candle.setHigh(new BigDecimal(candleData.get(2)));
        candle.setLow(new BigDecimal(candleData.get(3)));
        candle.setClose(new BigDecimal(candleData.get(4)));
        candle.setVolume(new BigDecimal(candleData.get(5)));
        candle.setVolumeCcy(new BigDecimal(candleData.get(6)));
        candle.setVolCcyQuote(new BigDecimal(candleData.get(7)));
        candle.setConfirm(candleData.get(8));
        return candle;
    }

    @Data
    private static class ApiResponse<T> {
        private String code;
        private String msg;
        private T data;
    }
}
