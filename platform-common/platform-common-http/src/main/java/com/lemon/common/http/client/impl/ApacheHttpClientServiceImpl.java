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
    private final int CONNECTION_TIMEOUT = 10 * 1000;
    private final int SOCKET_TIMEOUT = 10 * 1000;
    private final int REQUEST_TIMEOUT = 10 * 1000;

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
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(PROTOCOL_HTTP, new PlainConnectionSocketFactory())
                    .register(PROTOCOL_HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200); //max connection

            myStrategy = new ConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    // Honor 'keep-alive' header
                    HeaderElementIterator it = new BasicHeaderElementIterator(
                            response.headerIterator(CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        HeaderElement he = it.nextElement();
                        String param = he.getName();
                        String value = he.getValue();
                        if (value != null && param.equalsIgnoreCase("timeout")) {
                            return Long.parseLong(value) * 1000;
                        }
                    }
                    HttpHost target = (HttpHost) context.getAttribute(
                            HttpClientContext.HTTP_TARGET_HOST);
                    if ("www.lemon.com".equalsIgnoreCase(target.getHostName())) {
                        //keep alive for 5 seconds only
                        return 20 * 1000;
                    } else {
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
                .setSocketTimeout(SOCKET_TIMEOUT).setCookieSpec("JSESSIONID=5C8F75FEA4E220C6F510C11BDD070A8F.tiapp-prod-5-node5; CONSENTMGR=ts:1631152650790%7Cconsent:true; tiSessionID=017bc8463a280001f531ffaeeb7103072001606a0086e; user_pref_language=\"zh-CN\"; _ga=GA1.3.2142503932.1631152651; _gcl_au=1.1.1137003333.1631152652; __adroll_fpc=ef550055bc66216ea445f29123c0fef1-1631152652300; ELOQUA=GUID=B54CE10FBAB146CA9A34300715A8F31C; user_pref_noTransMsg=\"true\"; ti_geo=country=CN|city=NANCHANG|continent=AS|tc_ip=223.84.70.250; ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f93.0.4577.63%20Safari%2f537.36; ti_bm=; ak_bmsc=F99E824A887FB0A14EED5EAE90077249~000000000000000000000000000000~YAAQN/h33zdBRZ17AQAAGJzB0w1DTZy0vgfYf0AjXzZUIu9pqZtHVDFxYkbs/O6KQVY6XqVWI+LWHYebKaLXOJeikStdqywlF2aYTTgBsl/33aGtQxVVnXINkPrABwHDrw/5O6yLavIZw+sF7TBYBCRl3oL/TS+z1wJ1+weYlYBxcBElVbn2UvFaaWkXRdVtZ0jTaZVqWPHOCPtYXhgZ3aGL6aWJNaYfT7hChuVjFp+KR0Wn3byqUCXtBVpH8hdidckR4sBdaiwIiz0EYxDzWDQ8I0ULCJ4aQmkq5COP7KQ/08sTBbgKXZlbIvKRTOwtsvYVcsIoFI0r3ZT1n2n4rHL9d6XZn2WE+Uyru8Wax9U5OL7IG3Z63v6ifUhl85Q5QR+MfXWhXupG4XjuZsR/GXWqM+AGLpwlbc0qPmrPMeEQvseHHX4NCWkr7ZrtXMmo4mq7BU7FVUZKatJuhKK8Gq9AsS53KYEI8A6EEtE=; _gid=GA1.3.1160639597.1631345291; user_pref_givenNameLocalLanguage=\"fu\"; login-check=null; alias=homepageproduct; login-check=null; last-domain=www.ti.com.cn; user_pref_shipTo=\"CN\"; user_pref_currency=\"CNY\"; user_pref_permanentId=6794728; user_pref_givenName=fu; user_pref_uid=854757115@qq.com; acceleratorSecureGUID=09bc68b2a6bf4393130aecabdf79a1525680761a; auth_session=okuYuBgWHZjs_DTA.h9CcoAOIhoSvx-Ex8InUkNTTEpTM73eUzxtrWN0mU2PNNA3MY6l5UMMOnYCCT_Lt5H8Vr8T1CvjQtmX-pEjW15gWpLg4J4UeNucrguDYnJIHVY_3fPtZok3lQsisynYSAFN6wVR-ExNlg3xG10E_u5a-CXejvfkwbrCykirWAVma3BUHlKepVEdj_wo9QqFUYZGexQzLMhR4qCxV1fnb6Xcmi-W_oKr3EDwPZBBMUYynkz5C5EZhv8s8Dg1EFUGigA5TWtc40OhiwI6T0UvhjmRHG6visNblAIjeFIUZjhuY3_WiUQ4w-YXDMWcGqB3NEiBSXCRWztXpsD-OaRbRi5AEezRCKko0jO5u7iOEZO3Vm0k4YvSL1M4E6WEySQwVWJyjVMrOzg7vRMuhlPF_4IFF2_lAXQZLjT-2e_tmZatf8g1cX6r7-zI16E7l-Oa_Bvsbha7EQuJi2naWTSRXBIJFNIryZBWg8AnDncnFydxFz1iIwJRruHdyOpTRDwTeSLumxDSXrUzbLYUP11VWKSy_YMlDRhSfos05JxZGry1Q-Wfqj-ka6Bv_ZXHSLNuudNi70dLuYV7To-0Hp9W07T-94grIup1nwsAzMRrbljOkaSUfDcbbSUqnvq3nvCCYD0pz4FZUqhDDepYFziUpkg8ZiqRmlKWALrMmC-BoZsZBlg5xrKZ4TDd67hxIxurguzWGh8mIM0RPxKsy1zwqQR3qR6h23q8VKX8g22AAyU-XKAXlti1zvVWNgvT8tgkgKN-XfY4N_m7qRh0bQLaXbZQYfUTq1VbZHb_hQoa75T6_f0S8TCzf_Rbn3FMlyne5JAoW0-xdNyybXWrGMj5QQCbnbbNzMP2Hxm9Ch-5n1gKhWzp0y85c5eW8YAXdZ5SA4QqUp3P5CkWXE_ag9Q87UNM7vvDO62wTX3mvfGOSTeAh9vG1mKe6-u8fq7sSNBOtEMK6PvxJfSi7VO_Rua1dvBte-5S4aEGSW_FvNY_9bMTTV9W59LODP9xjc7bW3yzD2WOdQpEystDOQtl1hnpWa8Ajmg3NLAVRwyX8E6ekm0mhOAYAHEqaMxxai8syftwuC7XPWubYiEUgmARQEHB38hGWjs6tU4ird-iQtnXpOfNUmVLlEp0caxtNNM1KObwu1Rri6AXxGxMZAKj30ntvQZ3eSs0AeTRhAqg1buxiV_9J1rvHYHyaPkqfhpMDFnO1J8t7_8MrQsDxXCTTZ7rg2IxcYFBnAM0byuypQSlQUgGTSpybf_zgVP03OkUa4QsMSbNmlmGKijeyMT8EBt_hXc1u-Z5JGgB2SfAlQeCACRrRg1y_MEAWBsVe2pXy1yHf57XToR2pA1KkGDs5I-AQCnZ8y8S0r3oaPHOrriS_B-QiiCS8lY6z3MB7lmn6chl7dM9QrA4JOYMxWm9EURwDxThsqUk6Z_TdMCCLCav3drndbZGUHczi3SfEO9hdJAf4SnHrF-6Jrx-hc4CQ7_ckxTyOV7lDS01Ip0S4f7Z8Z66pfla32cbWYFNNyv3b6YE0X2qP8OiAWIlsddqn0BsFuPhQHG5Eso82RDfX7ESNtdzuhOvkQR_VC77q2M7wl5xCvYZIvvmDhlbZwf5bE1rd5saXfTjWM0fJh4VSA5klEALPpLXyJmyk_7_d_aHglq2k-C4OjwhoNhaRllPRhd1CEaM_co265-aOVTibLvs8nz86wREkj-tnubP5VkI2Z_J6vmQrCwOouVDHNV-kZYTFn_Jhl1Nd9EvzXlFTWmCjgBwfjI9CBrMPLKa6nm1yTxGDpFhhS2sBI8-GHKN5PkZOKzYTXrz_5SdgeGszn2A4ZwK7wO_Rv6EOcAHuoDtxGExrKjEqAjH_JZk3B8HvZ84Vod6AGGsS0vCd047zMLkJlvCDWcci7UHj_GFFeoiPmZsF0O1OdmL7zDNJJZdNaOQI_ghtDl9wnAOL9dA3nia7aBJ_s2lWQz-vgAnOWHk1up_U-IX5sI2PlrAnnJcA7HKu-tA.l-KWf8gRo6NA-KP56Iq2VQ; bm_sz=0B8DE10D96D2DB082FA0644F72C0EDA2~YAAQN/h330wVRp17AQAAeokC1A2RCElkKswbdONJ97tD8mIDg6yiqe9LWDf6MbxYRKJfspnjR4sNDipXkTxpSt9xUnvJwKn2WEvXIJkYLKZzaQe68a4fVwRy1jfC1gZ9X7mg4UalPJYxlq2MVnO49HeohLrGz4DaKq33xwymCdlgAEk6Rt/G4GPwKiiewvDawDDhpWGfTBDDVt5LLFYduHURxSSCPMXvffu/vgF6PmWbqbdOQWsHKhrb8dgT7yuE+QPtk4BguSraLl5LavCjX4pJ4IIwJ/1ZK9dgTn+gIJNs5BK4hbAU4T4AOoyQmg82csz5i+eJi9SOHw==~3355201~3355716; __ar_v4=G3YHLXUICZC3XKDXYVVLO4%3A20210909%3A17%7C2XNKMR6P4VGD5MD3ZP4SQR%3A20210909%3A17%7CQFXRHQEHOJDMLHSLFIWCLO%3A20210909%3A17; tipage=%2Ftistore%2Fsemiconductors%20new%20products%20-%20cn; tipageshort=semiconductors%20new%20products%20-%20cn; ticontent=%2Ftistore; ga_page_cookie=semiconductors%20new%20products%20-%20cn; ga_content_cookie=%2Ftistore; userType=Anonymous; _gat_ga_main_tracker=1; gpn=Non-Product; ABTasty=uid=7epzhakgx2k19e8k&fst=1631152651692&pst=1631347785195&cst=1631351568503&ns=6&pvt=47&pvis=2&th=686831.851794.23.2.3.1.1631187079773.1631351943648.1_730058.907046.1.1.1.1.1631345388031.1631345388031.1; ABTastySession=mrasn=&sen=3&lp=https%253A%252F%252Fwww.ti.com.cn%252Fprod-list%252Fcn%252Fnew-products%253FreleasePeriod%253D364; bm_sv=D55773AFF3A156C47337E2D493F6858B~4rkJMDhl/k+ba8lQyCHWW0ZvTjqLPL/CJJjL88Yhmsq4DGjzebGwOgiyuF5KvqsVU1eXT9Hh/pT4oILqXDXkrUxyWh4ZWI+2XapGegnuR46Kmc5oaavY935Nqo6oCCGEAuDqnvU1DPVdVwmsAozOwe8SHTq9eezgTxMPCEDWP/U=; utag_main=v_id:017bc8463a280001f531ffaeeb7103072001606a0086e$_sn:5$_ss:0$_st:1631353743965$free_trial:true$channel:cpc%3Bexp-1633937370413$ctimestamp:Sat%20Sep%2011%202021%2015%3A29%3A30%20GMT%2B0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)%3Bexp-1633937370413$_pn:2%3Bexp-session$ses_id:1631351567205%3Bexp-session; ti_rid=d51eac3; _abck=498CF072614B640541AB4B8A42C03897~0~YAAQD/B338xhI817AQAAYTMn1AYeX/aSdD1tUfoy8LlHym3rdc/AnvNOEs/7Beh6/6yOUxTvp6TapbJcUhjeO9CBMEUU9IRy2rdSj7F2bHfctduneNeVW5WIUN/8k7kqraUVPPzg61QKKGzDlW4PHJ4CdBTr5wv5SFB0yZWrC2vIoTP0ftUvSN+dn4th6m940aL0MMUYrgP7iWzbhQ5d3i4TX8HtTtCuxmJ1wnZcB61mIS8TUdI11Xtrg2Ofzy94lZhEhEGwrMHC0fQuZM7V1YKTzguulrBaybrNKQhk8uf2tGzoZ/Tb9EWxO2p4jKQEbNX9EAlOGkQ1I/5AtDxM0rsAefYZmxjhZQknVV6Pju4Berubnfk6AztVW36r~-1~-1~-1; da_sid=8FEE33EB8E32AE82A59DAA134F82596E2D|4|0|3; da_lid=6F461C239A73EA10740EBB990D9C745959|0|0|0; da_intState=")
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .build();
        httpPost.setConfig(config);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String val = String.valueOf(params.get(key));
            nvps.add(new BasicNameValuePair(key, val));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        return execute(httpPost);
    }

    private HttpResponseWrapper execute(HttpUriRequest httpUriRequest) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        try {
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
            return httpClient.execute(httpUriRequest, responseHandler);
        } finally {
            httpClient.close();
        }

    }

    private CloseableHttpClient getHttpClient() {
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
        return postJson(url, json, CONNECTION_TIMEOUT * 3, SOCKET_TIMEOUT * 3, REQUEST_TIMEOUT * 3);
    }

    /**
     * 调用腾讯小程序码专用 请求成功返回字节流 请求失败返回Json格式
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    @Override
    public <T> T tencentPostJson(String url, String json) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT * 3)        //	设置连接超时参数
                .setSocketTimeout(SOCKET_TIMEOUT * 10)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT * 3)
                .build();
        httppost.setConfig(config);

        httppost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        try {
            StringEntity se = new StringEntity(json);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(org.apache.http.protocol.HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httppost.setEntity(se);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //返回的是JSON直接返回 Json级别错误 需要在外部区分返回类型 做部分判断逻辑
                    if (entity.getContentType().getValue().matches("(.*)json(.*)")) {
                        return (T) EntityUtils.toString(entity, "UTF-8");
                    }
                    return (T) EntityUtils.toByteArray(entity);
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (T) new byte[]{};
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

        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        try {
            StringEntity se = new StringEntity(json);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            //关闭连接，释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
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
        while (iter.hasNext()) {
            String key = iter.next();
            String val = String.valueOf(params.get(key));
            uriBuilder.setParameter(key, val);
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setCookieSpec("JSESSIONID=5C8F75FEA4E220C6F510C11BDD070A8F.tiapp-prod-5-node5; CONSENTMGR=ts:1631152650790%7Cconsent:true; tiSessionID=017bc8463a280001f531ffaeeb7103072001606a0086e; user_pref_language=\"zh-CN\"; _ga=GA1.3.2142503932.1631152651; _gcl_au=1.1.1137003333.1631152652; __adroll_fpc=ef550055bc66216ea445f29123c0fef1-1631152652300; ELOQUA=GUID=B54CE10FBAB146CA9A34300715A8F31C; user_pref_noTransMsg=\"true\"; ti_geo=country=CN|city=NANCHANG|continent=AS|tc_ip=223.84.70.250; ti_ua=Mozilla%2f5.0%20(Windows%20NT%2010.0%3b%20Win64%3b%20x64)%20AppleWebKit%2f537.36%20(KHTML,%20like%20Gecko)%20Chrome%2f93.0.4577.63%20Safari%2f537.36; ti_bm=; ak_bmsc=F99E824A887FB0A14EED5EAE90077249~000000000000000000000000000000~YAAQN/h33zdBRZ17AQAAGJzB0w1DTZy0vgfYf0AjXzZUIu9pqZtHVDFxYkbs/O6KQVY6XqVWI+LWHYebKaLXOJeikStdqywlF2aYTTgBsl/33aGtQxVVnXINkPrABwHDrw/5O6yLavIZw+sF7TBYBCRl3oL/TS+z1wJ1+weYlYBxcBElVbn2UvFaaWkXRdVtZ0jTaZVqWPHOCPtYXhgZ3aGL6aWJNaYfT7hChuVjFp+KR0Wn3byqUCXtBVpH8hdidckR4sBdaiwIiz0EYxDzWDQ8I0ULCJ4aQmkq5COP7KQ/08sTBbgKXZlbIvKRTOwtsvYVcsIoFI0r3ZT1n2n4rHL9d6XZn2WE+Uyru8Wax9U5OL7IG3Z63v6ifUhl85Q5QR+MfXWhXupG4XjuZsR/GXWqM+AGLpwlbc0qPmrPMeEQvseHHX4NCWkr7ZrtXMmo4mq7BU7FVUZKatJuhKK8Gq9AsS53KYEI8A6EEtE=; _gid=GA1.3.1160639597.1631345291; user_pref_givenNameLocalLanguage=\"fu\"; login-check=null; alias=homepageproduct; login-check=null; last-domain=www.ti.com.cn; user_pref_shipTo=\"CN\"; user_pref_currency=\"CNY\"; user_pref_permanentId=6794728; user_pref_givenName=fu; user_pref_uid=854757115@qq.com; acceleratorSecureGUID=09bc68b2a6bf4393130aecabdf79a1525680761a; auth_session=okuYuBgWHZjs_DTA.h9CcoAOIhoSvx-Ex8InUkNTTEpTM73eUzxtrWN0mU2PNNA3MY6l5UMMOnYCCT_Lt5H8Vr8T1CvjQtmX-pEjW15gWpLg4J4UeNucrguDYnJIHVY_3fPtZok3lQsisynYSAFN6wVR-ExNlg3xG10E_u5a-CXejvfkwbrCykirWAVma3BUHlKepVEdj_wo9QqFUYZGexQzLMhR4qCxV1fnb6Xcmi-W_oKr3EDwPZBBMUYynkz5C5EZhv8s8Dg1EFUGigA5TWtc40OhiwI6T0UvhjmRHG6visNblAIjeFIUZjhuY3_WiUQ4w-YXDMWcGqB3NEiBSXCRWztXpsD-OaRbRi5AEezRCKko0jO5u7iOEZO3Vm0k4YvSL1M4E6WEySQwVWJyjVMrOzg7vRMuhlPF_4IFF2_lAXQZLjT-2e_tmZatf8g1cX6r7-zI16E7l-Oa_Bvsbha7EQuJi2naWTSRXBIJFNIryZBWg8AnDncnFydxFz1iIwJRruHdyOpTRDwTeSLumxDSXrUzbLYUP11VWKSy_YMlDRhSfos05JxZGry1Q-Wfqj-ka6Bv_ZXHSLNuudNi70dLuYV7To-0Hp9W07T-94grIup1nwsAzMRrbljOkaSUfDcbbSUqnvq3nvCCYD0pz4FZUqhDDepYFziUpkg8ZiqRmlKWALrMmC-BoZsZBlg5xrKZ4TDd67hxIxurguzWGh8mIM0RPxKsy1zwqQR3qR6h23q8VKX8g22AAyU-XKAXlti1zvVWNgvT8tgkgKN-XfY4N_m7qRh0bQLaXbZQYfUTq1VbZHb_hQoa75T6_f0S8TCzf_Rbn3FMlyne5JAoW0-xdNyybXWrGMj5QQCbnbbNzMP2Hxm9Ch-5n1gKhWzp0y85c5eW8YAXdZ5SA4QqUp3P5CkWXE_ag9Q87UNM7vvDO62wTX3mvfGOSTeAh9vG1mKe6-u8fq7sSNBOtEMK6PvxJfSi7VO_Rua1dvBte-5S4aEGSW_FvNY_9bMTTV9W59LODP9xjc7bW3yzD2WOdQpEystDOQtl1hnpWa8Ajmg3NLAVRwyX8E6ekm0mhOAYAHEqaMxxai8syftwuC7XPWubYiEUgmARQEHB38hGWjs6tU4ird-iQtnXpOfNUmVLlEp0caxtNNM1KObwu1Rri6AXxGxMZAKj30ntvQZ3eSs0AeTRhAqg1buxiV_9J1rvHYHyaPkqfhpMDFnO1J8t7_8MrQsDxXCTTZ7rg2IxcYFBnAM0byuypQSlQUgGTSpybf_zgVP03OkUa4QsMSbNmlmGKijeyMT8EBt_hXc1u-Z5JGgB2SfAlQeCACRrRg1y_MEAWBsVe2pXy1yHf57XToR2pA1KkGDs5I-AQCnZ8y8S0r3oaPHOrriS_B-QiiCS8lY6z3MB7lmn6chl7dM9QrA4JOYMxWm9EURwDxThsqUk6Z_TdMCCLCav3drndbZGUHczi3SfEO9hdJAf4SnHrF-6Jrx-hc4CQ7_ckxTyOV7lDS01Ip0S4f7Z8Z66pfla32cbWYFNNyv3b6YE0X2qP8OiAWIlsddqn0BsFuPhQHG5Eso82RDfX7ESNtdzuhOvkQR_VC77q2M7wl5xCvYZIvvmDhlbZwf5bE1rd5saXfTjWM0fJh4VSA5klEALPpLXyJmyk_7_d_aHglq2k-C4OjwhoNhaRllPRhd1CEaM_co265-aOVTibLvs8nz86wREkj-tnubP5VkI2Z_J6vmQrCwOouVDHNV-kZYTFn_Jhl1Nd9EvzXlFTWmCjgBwfjI9CBrMPLKa6nm1yTxGDpFhhS2sBI8-GHKN5PkZOKzYTXrz_5SdgeGszn2A4ZwK7wO_Rv6EOcAHuoDtxGExrKjEqAjH_JZk3B8HvZ84Vod6AGGsS0vCd047zMLkJlvCDWcci7UHj_GFFeoiPmZsF0O1OdmL7zDNJJZdNaOQI_ghtDl9wnAOL9dA3nia7aBJ_s2lWQz-vgAnOWHk1up_U-IX5sI2PlrAnnJcA7HKu-tA.l-KWf8gRo6NA-KP56Iq2VQ; bm_sz=0B8DE10D96D2DB082FA0644F72C0EDA2~YAAQN/h330wVRp17AQAAeokC1A2RCElkKswbdONJ97tD8mIDg6yiqe9LWDf6MbxYRKJfspnjR4sNDipXkTxpSt9xUnvJwKn2WEvXIJkYLKZzaQe68a4fVwRy1jfC1gZ9X7mg4UalPJYxlq2MVnO49HeohLrGz4DaKq33xwymCdlgAEk6Rt/G4GPwKiiewvDawDDhpWGfTBDDVt5LLFYduHURxSSCPMXvffu/vgF6PmWbqbdOQWsHKhrb8dgT7yuE+QPtk4BguSraLl5LavCjX4pJ4IIwJ/1ZK9dgTn+gIJNs5BK4hbAU4T4AOoyQmg82csz5i+eJi9SOHw==~3355201~3355716; __ar_v4=G3YHLXUICZC3XKDXYVVLO4%3A20210909%3A17%7C2XNKMR6P4VGD5MD3ZP4SQR%3A20210909%3A17%7CQFXRHQEHOJDMLHSLFIWCLO%3A20210909%3A17; tipage=%2Ftistore%2Fsemiconductors%20new%20products%20-%20cn; tipageshort=semiconductors%20new%20products%20-%20cn; ticontent=%2Ftistore; ga_page_cookie=semiconductors%20new%20products%20-%20cn; ga_content_cookie=%2Ftistore; userType=Anonymous; _gat_ga_main_tracker=1; gpn=Non-Product; ABTasty=uid=7epzhakgx2k19e8k&fst=1631152651692&pst=1631347785195&cst=1631351568503&ns=6&pvt=47&pvis=2&th=686831.851794.23.2.3.1.1631187079773.1631351943648.1_730058.907046.1.1.1.1.1631345388031.1631345388031.1; ABTastySession=mrasn=&sen=3&lp=https%253A%252F%252Fwww.ti.com.cn%252Fprod-list%252Fcn%252Fnew-products%253FreleasePeriod%253D364; bm_sv=D55773AFF3A156C47337E2D493F6858B~4rkJMDhl/k+ba8lQyCHWW0ZvTjqLPL/CJJjL88Yhmsq4DGjzebGwOgiyuF5KvqsVU1eXT9Hh/pT4oILqXDXkrUxyWh4ZWI+2XapGegnuR46Kmc5oaavY935Nqo6oCCGEAuDqnvU1DPVdVwmsAozOwe8SHTq9eezgTxMPCEDWP/U=; utag_main=v_id:017bc8463a280001f531ffaeeb7103072001606a0086e$_sn:5$_ss:0$_st:1631353743965$free_trial:true$channel:cpc%3Bexp-1633937370413$ctimestamp:Sat%20Sep%2011%202021%2015%3A29%3A30%20GMT%2B0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)%3Bexp-1633937370413$_pn:2%3Bexp-session$ses_id:1631351567205%3Bexp-session; ti_rid=d51eac3; _abck=498CF072614B640541AB4B8A42C03897~0~YAAQD/B338xhI817AQAAYTMn1AYeX/aSdD1tUfoy8LlHym3rdc/AnvNOEs/7Beh6/6yOUxTvp6TapbJcUhjeO9CBMEUU9IRy2rdSj7F2bHfctduneNeVW5WIUN/8k7kqraUVPPzg61QKKGzDlW4PHJ4CdBTr5wv5SFB0yZWrC2vIoTP0ftUvSN+dn4th6m940aL0MMUYrgP7iWzbhQ5d3i4TX8HtTtCuxmJ1wnZcB61mIS8TUdI11Xtrg2Ofzy94lZhEhEGwrMHC0fQuZM7V1YKTzguulrBaybrNKQhk8uf2tGzoZ/Tb9EWxO2p4jKQEbNX9EAlOGkQ1I/5AtDxM0rsAefYZmxjhZQknVV6Pju4Berubnfk6AztVW36r~-1~-1~-1; da_sid=8FEE33EB8E32AE82A59DAA134F82596E2D|4|0|3; da_lid=6F461C239A73EA10740EBB990D9C745959|0|0|0; da_intState=")
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
