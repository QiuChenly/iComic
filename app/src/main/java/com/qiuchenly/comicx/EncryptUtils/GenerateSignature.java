package com.qiuchenly.comicx.EncryptUtils;

import android.util.Log;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class GenerateSignature {
    public static final String TAG = GenerateSignature.class.getSimpleName();
    protected static final char[] hexArray = "0123456789abcdef".toCharArray();
    String signature;
    String url;

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[(bytes.length * 2)];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[(j * 2) + 1] = hexArray[v & 15];
        }
        return new String(hexChars);
    }

    public synchronized String getSignature(String raw, String hashKey) {
        byte[] key = new byte[0];
        String hkey = "F94612C9E58AA1B4BFF6DE29D77E6";
        key = hashKey.getBytes(StandardCharsets.UTF_8);
        String rawSignature = raw.toLowerCase();
        Log.d(TAG, "RAW SIGNATURE = " + rawSignature);
        this.signature = hashToString(rawSignature, key);
        return this.signature;
    }

    protected String hashToString(String message, byte[] key) {
        String result = null;
        try {
            Mac sha512_HMAC = Mac.getInstance("HmacSHA256");
            sha512_HMAC.init(new SecretKeySpec(key, "HmacSHA256"));
            return bytesToHex(sha512_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }
}