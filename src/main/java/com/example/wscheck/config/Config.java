package com.example.wscheck.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

    @Value("${okx.api.base-url:https://www.okx.com}")
    private String okxBaseUrl;

    @Bean
    public RestTemplate okxRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String okxBaseUrl() {
        return okxBaseUrl;
    }
}
