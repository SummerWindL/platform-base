package com.lemon.common.http.client;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-07-03 11:01
 **/

public interface HttpClientService {
    HttpResponseWrapper post(String url, Map<String,Object> params) throws IOException, URISyntaxException;
    HttpResponseWrapper post(String url, Object params) throws IOException, URISyntaxException;

    /**
     * 使用默认超时参数（10秒）的 POST JSON
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    String postJson(String url,String json) throws IOException;

    /**
     * 调用腾讯小程序码专用 请求成功返回字节流 请求失败返回Json格式
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    <T> T tencentPostJson(String url, String json) throws IOException;
    /**
     * 支持设置参数超时的 POST JSON 单位是毫秒
     * @param url
     * @param json
     * @param connectionOut
     * @param socketOut
     * @param requestOut
     * @return
     * @throws IOException
     */
    public String postJson(String url,String json,int connectionOut,int socketOut,int requestOut) throws IOException;

    HttpResponseWrapper get(String url,Map<String, Object> params) throws IOException,URISyntaxException;

    HttpResponseWrapper get(String url,Object params) throws IOException,URISyntaxException;

    /** 上传文件 **/
    HttpResponseWrapper postFile(String url, Map<String,Object>params, File file) throws IOException;

}
