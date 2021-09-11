package com.platform.comservice.service.impl;

import cn.hutool.json.JSONUtil;
import com.cluster.platform.redis.ICache;
import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.HttpResponseWrapper;
import com.platform.comservice.bean.Auth;
import com.platform.comservice.config.ComServiceProperties;
import com.platform.comservice.service.TencentMiniProApiService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 腾讯小程序码获取
 * @author: fuyl
 * @create: 2020-09-10 17:17
 **/
@Slf4j
@Service
public class TencentMiniProApiServiceImpl implements TencentMiniProApiService {

    // 默认 一个半小时过期
    private static final Long EXPIRETIME = 10 * 540L;

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_CREDENTIAL = "client_credential";
    private static final String APPID = "appid";
    private static final String SECRET = "secret";
    private static final String BCODE_URL ="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";//获取授权码的请求必须是params带 access_token 其他参数以json传递
    private static final String ACODE_URL ="https://api.weixin.qq.com/wxa/getwxacode?access_token=";//获取授权码的请求必须是params带 access_token 其他参数以json传递
    private static final String ACCESSTOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token";


    @Autowired
    private ICache iCache;
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private ComServiceProperties comServiceProperties;

    /**
     * 获取小程序AccessToken
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html
     * @param appid
     * @param secret
     * @return
     */
    @Override
    public String getAuthAccessToken(String appid, String secret) {
        String accessToken = (String)iCache.get(appid);
        if(StringUtils.isEmpty(accessToken)){
            //查询access_token 并存入redis
            Map<String,Object> params = new HashMap<>();
            params.put(GRANT_TYPE,CLIENT_CREDENTIAL);
            params.put(APPID,appid);
            params.put(SECRET,secret);
            try {
                log.info("获取小程序凭证请求参数：{}",params.toString());
                HttpResponseWrapper httpResponseWrapper = httpClientService.get(ACCESSTOKEN_URL, params);
                Auth auth = JSONUtil.parseObj(httpResponseWrapper.getResponseBody()).toBean(Auth.class);
                iCache.setExpire(appid,auth.getAccess_token(),EXPIRETIME);
                return auth.getAccess_token();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        log.info("凭证未过期 从redis获取");
        return accessToken;
    }

    /**
     * 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
     * 接口B https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html
     * @param accessToken 接口调用凭证
     * @param scene       请求参数
     * @param path        跳转的页面
     * @param width       二维码的宽度
     * @param autoColor   自动配置线条颜色
     * @param lineColor   auto_color 为 false 时生效
     * @param isHyaline   是否需要透明底色
     * @return
     */
    @Override
    public String getUnlimitedWxBCode(String accessToken, String scene, String path, int width, boolean autoColor, Object lineColor, boolean isHyaline) {
        Object o = getBCode(accessToken,scene,path,width,autoColor,lineColor,isHyaline);
        return commonResult(o);
    }

    /**
     * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
     * 接口A https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.get.html
     * @param accessToken 接口调用凭证
     * @param path        跳转的页面 参数通过 ?a=1 类似传递
     * @param width       二维码的宽度
     * @param autoColor   自动配置线条颜色
     * @param lineColor   auto_color 为 false 时生效
     * @param isHyaline   是否需要透明底色
     * @return
     */
    @Override
    public String getWxACode(String accessToken, String path, int width, boolean autoColor, Object lineColor, boolean isHyaline) {
        Object o = getACode(accessToken, path, width, autoColor, lineColor, isHyaline);
        return commonResult(o);
    }

    /**
     * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
     *
     * @param accessToken 接口调用凭证
     * @param path        跳转的页面
     * @param width       二维码的宽度
     * @param autoColor   自动配置线条颜色
     * @param lineColor   auto_color 为 false 时生效
     * @param isHyaline   是否需要透明底色
     * @return T
     */
    @Override
    public <T> T getResultTWxACode(String accessToken, String path, int width, boolean autoColor, Object lineColor, boolean isHyaline) {
        return getACode(accessToken,path,width,autoColor,lineColor,isHyaline);
    }

    /**
     * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，无数量限制
     *
     * @param accessToken 接口调用凭证
     * @param scene
     * @param path        跳转的页面
     * @param width       二维码的宽度
     * @param autoColor   自动配置线条颜色
     * @param lineColor   auto_color 为 false 时生效
     * @param isHyaline   是否需要透明底色
     * @return T
     */
    @Override
    public <T> T getResultTWxBCode(String accessToken, String scene, String path, int width, boolean autoColor, Object lineColor, boolean isHyaline) {
        return getBCode(accessToken,scene,path,width,autoColor,lineColor,isHyaline);
    }


    /**
     * 小程序码 A接口
     * @param accessToken
     * @param path
     * @param width
     * @param autoColor
     * @param lineColor
     * @param isHyaline
     * @param <T>
     * @return
     */
    private <T> T getACode(String accessToken, String path, int width, boolean autoColor, Object lineColor, boolean isHyaline){
        JSONObject codeParams = new JSONObject();
        codeParams.put("path", path);
        codeParams.put("width", width);
        codeParams.put("auto_color", autoColor);
        codeParams.put("line_color",lineColor);
        codeParams.put("is_hyaline",isHyaline);
        Object o = null;
        log.info("获取小程序码请求参数：{}",codeParams.toString());
        o = postRequest(ACODE_URL + accessToken, codeParams.toString());
//            o = httpClientService.tencentPostJson(ACODE_URL+ accessToken, codeParams.toString());
        return (T) o;
    }

    /**
     * 小程序码 B接口
     * @param accessToken
     * @param scene
     * @param path
     * @param width
     * @param autoColor
     * @param lineColor
     * @param isHyaline
     * @param <T>
     * @return
     */
    private <T> T getBCode(String accessToken, String scene, String path, int width, boolean autoColor, Object lineColor, boolean isHyaline){
        JSONObject codeParams = new JSONObject();
        codeParams.put("scene", scene);
//        codeParams.put("path", path);
        codeParams.put("page", path);
        codeParams.put("width", width);
        codeParams.put("auto_color", autoColor);
        codeParams.put("line_color",lineColor);
        codeParams.put("is_hyaline",isHyaline);
        Object o = null;
        log.info("获取小程序码请求参数：{}",codeParams.toString());
        o = postRequest(BCODE_URL+ accessToken, codeParams.toString());
//        o = httpClientService.tencentPostJson(BCODE_URL+ accessToken, codeParams.toString());
        return (T) o;
    }

    /**
     * 直接返回byte数组 方便转成流 存oss
     * @param o
     * @return
     */
    @Override
    public byte[] byteResult(Object o){
        byte[] bytes = null;
        if(o instanceof byte[]) { //成功
            bytes = (byte[]) o;
            return bytes;
        }
        return null;
    }

    /**
     * 通用返回 请求成功返回base64编码的字符串 否则返回错误json字符串
     * @param o
     * @return
     */
    @Override
    public String commonResult(Object o){
        byte[] bytes = null;
        String resultStr = "";
        if(o instanceof byte[]){ //成功
            bytes = (byte[]) o;
            resultStr = new String(Base64.encodeBase64(bytes));
        }else if(o instanceof String){ //失败打印失败信息 TODO 是否需要同步返回失败信息？
            resultStr = o.toString();
            log.error("errorMessgae: {}",resultStr);
        }
        return resultStr;
    }

    /**
     * 请求发送
     * @param url
     * @param json
     * @return
     */
    private synchronized Object postRequest(String url, String json){
        try {
            return httpClientService.tencentPostJson(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
