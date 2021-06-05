package com.yunzhu.module.common.http;


import android.content.Context;

import com.yunzhu.module.common.R;
import com.yunzhu.module.common.base.BaseApplication;

import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author:lisc
 * @date: 2019-06-19
 * @description:
 */
public class SSLSocketClient {

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            Context appContext = BaseApplication.Companion.getInstance();
            String password = "123456";
            clientKeyStore.load(appContext.getResources().openRawResource(R.raw.client2), password.toCharArray());

//        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        //用我们之前的keyStore实例初始化TrustManagerFactory，这样trustManagerFactory就会信任keyStore中的证书
//        trustManagerFactory.init(clientKeyStore);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());

            sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{getTrustManager()}, new SecureRandom());
//        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static X509TrustManager getTrustManager(){
        TrustManagerFactory trustManagerFactory = null;
        X509TrustManager trustManager = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(
                              TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return trustManager;
    }


    /**
     * 对服务器证书域名进行强校验
     */
    private static class SafeTrustManager implements X509TrustManager {
        private X509Certificate mCertificate;

        private SafeTrustManager(){}
        private SafeTrustManager(X509Certificate serverCert) {
            mCertificate = serverCert;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
            if (x509Certificates == null) {
                throw new IllegalArgumentException("Check Server x509Certificates is null");
            }

            if (x509Certificates.length < 0) {
                throw new IllegalArgumentException("Check Server x509Certificates is empty");
            }

            try {
                for (X509Certificate cert : x509Certificates) {
                    // Make sure that it hasn't expired.
                    cert.checkValidity();
                    //和App预埋的证书做对比
                    cert.verify(mCertificate.getPublicKey());

                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                e.printStackTrace();
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
} 