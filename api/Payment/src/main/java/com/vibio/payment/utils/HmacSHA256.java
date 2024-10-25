/*
 *  hmacSHA256
 *  @author: Minhhieuano
 *  @created 10/24/2024 5:26 PM
 * */


package com.vibio.payment.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class HmacSHA256 {
    public static String hmacSHA256(String data, String key) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);

        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for (byte b : signedBytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }
}
