package com.example.wsq.library.okhttp.ssl;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * https请求工具类
 */
public class HttpsUtils {

    public static synchronized OkHttpClient getClient(OkHttpClient mOkHttpClient) {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            try {
                // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                final X509TrustManager trustAllCert =
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        };
                final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                builder.sslSocketFactory(sslSocketFactory, trustAllCert);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mOkHttpClient = builder.build();
        }

        return mOkHttpClient;
    }
}
