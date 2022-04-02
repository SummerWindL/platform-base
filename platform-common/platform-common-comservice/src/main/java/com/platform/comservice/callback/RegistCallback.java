package com.platform.comservice.callback;

/**
 * 登记校验回调函数
 * @author Advance
 * @date 2022年03月19日 17:36
 * @since V1.0.0
 */
public class RegistCallback {

    /**
     * 登记回调
     * @author Advance
     * @date 2022/3/19 17:37
     * @param callBack
     */
    public void callback(CommonCheckCallBack callBack){
        System.out.println("登记回调");
        callBack.slove();
    }
}
