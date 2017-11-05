package io.tapack.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;

public final class SSLUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSLUtils
            .class);

    private SSLUtils() {
    }

    public static void doTrustToCertificates() throws KeyManagementException,
            NoSuchAlgorithmException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new TrustAllTrustManager();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(getHostNameVerifier());
    }

    private static HostnameVerifier getHostNameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                String peerHost = session.getPeerHost();
                if (!urlHostName.equalsIgnoreCase(peerHost)) {
                    LOGGER.warn("Warning: URL host '" + urlHostName + "' is " +
                            "different to SSLSession host '" + peerHost + "'.");
                }
                return true;
            }
        };
    }

    private static class TrustAllTrustManager implements TrustManager,
            X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
        }
    }
}
