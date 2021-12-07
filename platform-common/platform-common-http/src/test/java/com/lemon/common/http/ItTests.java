package com.lemon.common.http;

import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.HttpResponseWrapper;
import com.platform.common.util.JsonAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Advance
 * @date 2021年09月09日 9:59
 * @since V1.0.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpApplication.class)
public class ItTests {
    @Autowired
    HttpClientService httpClientService;
    @Autowired
    private JsonAdaptor jsonAdaptor;

    String authUrl = "https://login.ti.com/as/authorization.oauth2";

    String response_type = "code";
    String scope = "openid email profile";
    String client_id = "DCIT_ALL_WWW-PROD";
    String state = "KpTmLZ94iEFirUeRhJMRn0i_0ec";
    String redirect_uri = "https://www.ti.com.cn/oidc/redirect_uri/";
    String nonce = "DFP1Pmwm1N_5dG8QrUUBalBoCXdWY9XMLsfzcCR7G3g";
    String response_mode = "form_post";

    @Test
    public void oauth2test() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("response_type", response_type);
        params.put("scope", scope);
        params.put("client_id", client_id);
        params.put("state", state);
        params.put("redirect_uri", redirect_uri);
        params.put("nonce", nonce);
        params.put("response_mode", response_mode);
        try {
            HttpResponseWrapper get = httpClientService.get(authUrl, params);
            String responseBody = get.getResponseBody();
            int i = responseBody.indexOf("'action', ");
            System.out.println(responseBody.substring(i + 11, i + 49));
            //log.info("响应状态码：{},响应体：{}",get.getStatusCode(),get.getResponseBody());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void singlePageRequest() {
        String reqUrl = "https://ariane.abtasty.com/";
        Map<String, Object> params = new HashMap<String, Object>();
        sendRequest(reqUrl, params);
    }


    @Test
    public void resumeAuthRequest() {
        String resumeReqUrl = "https://login.ti.com/as/hSm4Q/resume/as/authorization.ping";
        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("method","post");
        params.put("pf.username", "854757115@qq.com");
        params.put("pf.pass", "Xjc.1123");
        params.put("pf.adapterId", "IDPAdapterHTMLFormCIDStandard");

        sendRequest(resumeReqUrl, params);

    }

    @Test
    public void redirectUrlReq() {
        String redirectUrl = "https://www.ti.com.cn/oidc/redirect_uri/";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", "Kxyn44fjiJnvxGxQSRNZSktw-3HmHnxSZ1wpriLe");
        params.put("state", "Xw_hrb7apNTmFfn4QGUzKOW7K3w");

        sendRequest(redirectUrl, params);
    }

    @Test
    public void YiYiI() {
        String yiyiI = "https://login.ti.com/m6UTjJqE/t6F/7ML/mRjrNjt_8P/ihafcpDr/W3lIdCkmegE/YXMRIn/YiYiI";
        Map<String, Object> params = new HashMap<String, Object>();
        getRequest(yiyiI, params);
    }

    @Test
    public void secCptJsReq() {
        String url = "https://login.ti.com/_sec/cp_challenge/sec-cpt-3-6.js";
        Map<String, Object> params = new HashMap<String, Object>();
        getRequest(url, params);
    }

    /**
     * 获取产品列表
     *
     * @author Advance
     * @date 2021/9/11 17:27
     */
    @Test
    public void getProdList() {
        String url = "https://www.ti.com.cn/prod-list/cn/new-products?releasePeriod=364";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("releasePeriod", "364");
        getRequest(url, params);
    }

    /**
     * 获取产品库存
     *
     * @author Advance
     * @date 2021/9/11 17:28
     */
    @Test
    public void getProdInventory() {
        String url = "https://www.ti.com.cn/storeservices/cart/opninventory?opn=DRA829VMTGBALFR"; //指定产品
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("opn", "DRA829VMTGBALFR");
        getRequest(url, params);
    }

    /**
     * 发送请求
     *
     * @param baseUrl
     * @param params
     * @author Advance
     * @date 2021/9/9 11:01
     */
    private void sendRequest(String baseUrl, Map<String, Object> params) {
        try {
            HttpResponseWrapper post = httpClientService.post(baseUrl, params);
            log.info("响应状态码：{},响应体：{}", post.getStatusCode(), post.getResponseBody());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求
     *
     * @param baseUrl
     * @param params
     * @author Advance
     * @date 2021/9/9 11:11
     */
    private HttpResponseWrapper getRequest(String baseUrl, Map<String, Object> params) {

        try {
            HttpResponseWrapper get = httpClientService.get(baseUrl, params);
            log.info("响应状态码：{},响应体：{}", get.getStatusCode(), get.getResponseBody());
            return get;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
