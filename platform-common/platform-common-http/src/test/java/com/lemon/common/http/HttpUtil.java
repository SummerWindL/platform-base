/**
 * 文件名:HttpUtil.java
 * 版权:ikinloop
 * 描述:
 * 修改人:rivers
 * 修改时间:2017年6月19日
 * 修改内容:
 * 跟踪单号
 * 修改单号
 */

package com.lemon.common.http;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author rivers
 * @version 2.0
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HttpUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    private static ConnectionKeepAliveStrategy myStrategy = null;

    static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection

            myStrategy = new ConnectionKeepAliveStrategy() {

                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    // Honor 'keep-alive' header
                    HeaderElementIterator it = new BasicHeaderElementIterator(
                            response.headerIterator(org.apache.http.protocol.HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();
                        if (value != null && param.equalsIgnoreCase("timeout")) {
                            try {
                                return Long.parseLong(value) * 1000;
                            } catch (NumberFormatException ignore) {
                            }
                        }
                    }
                    HttpHost target = (HttpHost) context.getAttribute(
                            HttpClientContext.HTTP_TARGET_HOST);
                    if ("www.iiecg.com".equalsIgnoreCase(target.getHostName())) {
                        // Keep alive for 5 seconds only
                        return 20 * 1000;
                    } else {
                        // otherwise keep alive for 30 seconds
                        return 30 * 1000;
                    }
                }

            };

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static class HttpResponseWrapper {

        private int statusCode;

        private String responseBody;

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }


    }

    public static HttpResponseWrapper post(String url, Map<String, String> params) throws IOException {

        CloseableHttpClient httpclient = getHttpClient();
        try {

            HttpPost httpPost = new HttpPost(url);

            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .build();

            httpPost.setConfig(config);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator<String> iter = params.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                String val = params.get(key);
                nvps.add(new BasicNameValuePair(key, val));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));


            ResponseHandler<HttpResponseWrapper> responseHandler =
                    new ResponseHandler<HttpResponseWrapper>() {

                        @Override
                        public HttpResponseWrapper handleResponse(HttpResponse response)
                                throws ClientProtocolException, IOException {
                            HttpResponseWrapper responseWrapper = null;
                            if (response != null) {
                                responseWrapper = new HttpResponseWrapper();
                                int status = response.getStatusLine().getStatusCode();
                                responseWrapper.setStatusCode(status);

                                HttpEntity entity = response.getEntity();
                                if (entity != null) {
                                    responseWrapper.setResponseBody(EntityUtils.toString(entity));
                                }
                            }
                            return responseWrapper;
                        }

                    };
            return httpclient.execute(httpPost, responseHandler);
        } finally {
            httpclient.close();
        }
    }

    public static CloseableHttpClient getHttpClient() throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .setKeepAliveStrategy(myStrategy)
                .build();
        return httpClient;
    }

}
