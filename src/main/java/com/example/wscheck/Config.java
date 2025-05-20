package com.example.wscheck;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "okx")
public class Config {
    private WsConfig ws;
    private ApiConfig api;

    @Data
    public static class WsConfig {
        private String publicUrl;
        private String businessUrl;
    }

    @Data
    public static class ApiConfig {
        private String key;
        private String secret;
        private String passphrase;
    }
}
