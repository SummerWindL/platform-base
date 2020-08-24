package com.lemon.common.http.client.impl;

import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.HttpResponseWrapper;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.apache.http.protocol.HTTP.CONN_KEEP_ALIVE;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-07-03 11:02
 **/

public class ApacheHttpClientServiceImpl implements HttpClientService {

    private static final String PROTOCOL_HTTP = "http";
    private static final String PROTOCOL_HTTPS = "https";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";

    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    private static ConnectionKeepAliveStrategy myStrategy = null;
    private final int CONNECTION_TIMEOUT = 10 *1000;
    private final int SOCKET_TIMEOUT = 10 *1000;
    private final int REQUEST_TIMEOUT = 10 *1000;

    static {
        try {
            builder = new SSLContextBuilder();
            //全部信任 不作身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(),new String[]{"SSLv2Hello","SSLv3","TLSv1","TLSv1.2"},null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(PROTOCOL_HTTP,new PlainConnectionSocketFactory())
                    .register(PROTOCOL_HTTPS,sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200); //max connection

            myStrategy = new ConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    // Honor 'keep-alive' header
                    HeaderElementIterator it = new BasicHeaderElementIterator(
                            response.headerIterator(CONN_KEEP_ALIVE));
                    while(it.hasNext()){
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();
                        if(value != null && param.equalsIgnoreCase("timeout")){
                            return Long.parseLong(value) * 1000;
                        }
                    }
                    HttpHost target = (HttpHost) context.getAttribute(
                            HttpClientContext.HTTP_TARGET_HOST);
                    if("www.lemon.com".equalsIgnoreCase(target.getHostName())){
                        //keep alive for 5 seconds only
                        return 20 * 1000;
                    }else{
                        return 30 * 1000;
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApacheHttpClientServiceImpl() {
    }

    @Override
    public HttpResponseWrapper post(String url, Map<String, Object> params)
            throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .build();
        httpPost.setConfig(config);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Iterator<String> iter = params.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            String val = String.valueOf(params.get(key));
            nvps.add(new BasicNameValuePair(key,val));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
        return execute(httpPost);
    }

    private HttpResponseWrapper execute(HttpUriRequest httpUriRequest) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        try{
            ResponseHandler<HttpResponseWrapper> responseHandler =
                    new ResponseHandler<HttpResponseWrapper>() {
                        @Override
                        public HttpResponseWrapper handleResponse(HttpResponse response)
                                throws ClientProtocolException, IOException {
                            HttpResponseWrapper responseWrapper = null;
                            if(response != null){
                                responseWrapper = new HttpResponseWrapper();
                                int status = response.getStatusLine().getStatusCode();
                                responseWrapper.setStatusCode(status);

                                HttpEntity entity = response.getEntity();
                                if(entity != null){
                                    responseWrapper.setResponseBody(EntityUtils.toString(entity));
                                }
                            }
                            return responseWrapper;
                        }
                    };
            return httpClient.execute(httpUriRequest,responseHandler);
        }finally {
            httpClient.close();
        }

    }

    private CloseableHttpClient getHttpClient(){
        CloseableHttpClient httpClient = HttpClients.custom()
                /* .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .setKeepAliveStrategy(myStrategy)*/
                .build();
        return httpClient;
    }

    @Override
    public HttpResponseWrapper post(String url, Object params) throws IOException, URISyntaxException {
        HttpResponseWrapper rsp = new HttpResponseWrapper();
        return rsp;
    }

    /**
     * 使用默认超时参数（10秒）的 POST JSON
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    @Override
    public String postJson(String url, String json) throws IOException {
        return postJson (url, json, CONNECTION_TIMEOUT * 3, SOCKET_TIMEOUT * 3, REQUEST_TIMEOUT * 3);
    }

    /**
     * 支持设置参数超时的 POST JSON 单位是毫秒
     *
     * @param url
     * @param json
     * @param connectionOut
     * @param socketOut
     * @param requestOut
     * @return
     * @throws IOException
     */
    @Override
    public String postJson(String url, String json, int connectionOut, int socketOut, int requestOut) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectionOut)
                .setSocketTimeout(socketOut)
                .setConnectionRequestTimeout(requestOut)
                .build();
        httpPost.setConfig(config);

        httpPost.addHeader(HTTP.CONTENT_TYPE,APPLICATION_JSON);
        try{
            StringEntity se = new StringEntity(json);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(CONTENT_TYPE,APPLICATION_JSON));
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try{
                HttpEntity entity = response.getEntity();
                if(entity!= null){
                    return EntityUtils.toString(entity,"UTF-8");
                }
            }finally {
                response.close();
            }
        }catch (IOException e){
            throw e;
        } finally {
            //关闭连接，释放资源
            try{
                httpClient.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public HttpResponseWrapper get(String url, Map<String, Object> params)
            throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        Iterator<String> iter = params.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            String val = String.valueOf(params.get(key));
            uriBuilder.setParameter(key,val);
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .build();

        httpGet.setConfig(config);
        return execute(httpGet);
    }

    @Override
    public HttpResponseWrapper get(String url, Object params) throws IOException, URISyntaxException {
        HttpResponseWrapper rsp = new HttpResponseWrapper();
        return rsp;
    }

    /**
     * 上传文件
     *
     * @param url
     * @param params
     * @param file
     **/
    @Override
    public HttpResponseWrapper postFile(String url, Map<String, Object> params, File file) throws IOException {
        HttpPost httpPost = new HttpPost(url);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT * 3)
                .setSocketTimeout(SOCKET_TIMEOUT * 3)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT * 3)
                .build();
        httpPost.setConfig(config);

        for (String key : params.keySet()) {
            httpPost.addHeader(key, params.get(key).toString());
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        return execute(httpPost);
    }
}
