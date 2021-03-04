package com.qiuchenly.comicx.ProductModules.Bika;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class RestWakaClient {
    public static final String BASE_URL = "http://68.183.234.72/";
    public static final String BASE_URL_BACKUP = "http://206.189.95.169/";
    public static final String TAG = "RestWakaClient";
    private ApiService apiService;

    public RestWakaClient() {
        new HttpLoggingInterceptor().setLevel(Level.BODY);
        try {
            OkHttpClient.Builder client = new Builder();
//            client.connectTimeout(15, TimeUnit.SECONDS);
//            client.readTimeout(15, TimeUnit.SECONDS);
//            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
//            client.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager());
            this.apiService = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client.build()).build().create(ApiService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiService getApiService() {
        return this.apiService;
    }
}