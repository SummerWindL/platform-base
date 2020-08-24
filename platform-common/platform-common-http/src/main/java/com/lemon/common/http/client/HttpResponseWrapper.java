package com.lemon.common.http.client;

import lombok.Data;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-07-03 11:01
 **/
@Data
public class HttpResponseWrapper {
    private int statusCode;

    private String responseBody;
}
