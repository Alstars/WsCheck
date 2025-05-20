package com.example.wscheck;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class AuthUtils {

    public static String generateSignature(String secret, String timestamp, String method, String requestPath,
                                           String body) throws NoSuchAlgorithmException, InvalidKeyException {
        String preHash = timestamp + method + requestPath + (body != null ? body : "");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);
        byte[] hash = mac.doFinal(preHash.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static String getTimestamp() {
        return Instant.now().toString().substring(0, 23) + "Z";
    }
}
