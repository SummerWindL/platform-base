package com.platform.comservice.service;

/**
 * @description: 腾讯小程序接口服务 公用
 * @author: fuyl
 * @create: 2020-09-10 17:17
 **/

public interface TencentMiniProApiService {

    /**
     * 获取小程序AccessToken
     * @param appid
     * @param secret
     * @return
     */
    String getAuthAccessToken(String appid,String secret);

    /**
     * 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。
     * @param accessToken 接口调用凭证
     * @param scene 请求参数
     * @param path 跳转的页面
     * @param width 二维码的宽度
     * @param autoColor 自动配置线条颜色
     * @param lineColor auto_color 为 false 时生效
     * @param isHyaline 是否需要透明底色
     * @return
     */
    String getUnlimitedWxBCode(String accessToken,String scene,String path,int width,boolean autoColor,Object lineColor,boolean isHyaline);


    /**
     * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
     * @param accessToken 接口调用凭证
     * @param path 跳转的页面
     * @param width 二维码的宽度
     * @param autoColor 自动配置线条颜色
     * @param lineColor auto_color 为 false 时生效
     * @param isHyaline 是否需要透明底色
     * @return
     */
    String getWxACode(String accessToken,String path,int width,boolean autoColor,Object lineColor,boolean isHyaline);


    /**
     * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制
     * @param accessToken 接口调用凭证
     * @param path 跳转的页面
     * @param width 二维码的宽度
     * @param autoColor 自动配置线条颜色
     * @param lineColor auto_color 为 false 时生效
     * @param isHyaline 是否需要透明底色
     * @return T
     */
    <T> T getResultTWxACode(String accessToken,String path,int width,boolean autoColor,Object lineColor,boolean isHyaline);

    /**
     * 获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，无数量限制
     * @param accessToken 接口调用凭证
     * @param scene 请求参数
     * @param path 跳转的页面
     * @param width 二维码的宽度
     * @param autoColor 自动配置线条颜色
     * @param lineColor auto_color 为 false 时生效
     * @param isHyaline 是否需要透明底色
     * @return T
     */
    <T> T getResultTWxBCode(String accessToken,String scene,String path,int width,boolean autoColor,Object lineColor,boolean isHyaline);

    /**
     * 通用返回String字符串 请求成功返回base64编码的字符串 否则返回错误json字符串
     * @param o
     * @return
     */
    String commonResult(Object o);

    /**
     * 直接返回byte数组 方便转成流 存oss
     * @param o
     * @return
     */
    byte[] byteResult(Object o);
}
