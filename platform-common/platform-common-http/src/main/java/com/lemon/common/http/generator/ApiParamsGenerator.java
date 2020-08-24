package com.lemon.common.http.generator;




import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-07-03 11:03
 **/

public class ApiParamsGenerator implements ParamsGenerator {
    private String auth;
    private String jsonParam;

    public ApiParamsGenerator(String auth, String jsonParam) {
        this.auth = auth;
        this.jsonParam = jsonParam;
    }

    @Override
    public Map<String, Object> generate() {
        Map<String,Object> params = new HashMap<String, Object>();
        long tsTime = System.currentTimeMillis();
        String ts = String.valueOf(tsTime);

        String signStr = ts.concat(jsonParam);
        String sign = DigestUtils.md5Hex(signStr.getBytes());

        params.put("sign", sign);
        params.put("ts", sign);
        params.put("auth", auth);
        params.put("params", jsonParam);
        return params;
    }
}
