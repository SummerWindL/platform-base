package com.platform.comservice.miniprogramcode;

import cn.hutool.json.JSONUtil;
import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.HttpResponseWrapper;
import com.platform.comservice.PlatformComServiceApplicationTests;
import com.platform.comservice.service.TencentMiniProApiService;
import lombok.Data;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CodeGenerator
 * @Description
 * @Author yanl
 * @Date 2020/9/12 0:49
 * @Version 1.0
 **/
public class CodeGenerator extends PlatformComServiceApplicationTests {
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private TencentMiniProApiService tencentMiniProApiService;

    @Test
    public void test() throws IOException, URISyntaxException {
        //1.获取检测小程序access_token
        Map<String,Object> params = new HashMap<>();
        params.put("grant_type","client_credential");
        params.put("appid","wx968357120a739302");
        params.put("secret","2838a72831e5fd9a8adbe828a74c4d53");
        HttpResponseWrapper httpResponseWrapper = httpClientService.get("https://api.weixin.qq.com/cgi-bin/token", params);
        Auth auth = JSONUtil.parseObj(httpResponseWrapper.getResponseBody()).toBean(Auth.class);
        //2.post获取小程序码Buff
//        httpResponseWrapper.getResponseBody();
        JSONObject codeParams = new JSONObject();
//        codeParams.put("access_token",auth.getAccess_token());
        codeParams.put("scene", "a=1"); // 你要放的内容
        codeParams.put("path", "pages/home/HomePage");
        codeParams.put("width", 430); // 宽度
        codeParams.put("auto_color", true);
        printResult(codeParams);
        Object o = httpClientService.tencentPostJson("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + auth.getAccess_token(), codeParams.toString());
        byte[] bytes1 = null;
        if(o instanceof byte[]){
            bytes1 = (byte[]) o;
        }else if(o instanceof String){
            System.out.println(o.toString());
        }
//        postMiniqrQr("a=1",auth.getAccess_token());
        String bytes = new String(Base64.encodeBase64(bytes1));
//        String base64FromInputStream = getBase64FromInputStream(content);
        System.out.println(bytes);
    }


    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }

    @Data
    public static class Auth{

        private String access_token;
    }

    public static void postMiniqrQr(String scene, String accessToken) {
        try{
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true); // 打开写入属性
            httpURLConnection.setDoInput(true); // 打开读取属性
            httpURLConnection.setRequestMethod("POST");// 提交方式
            // 不得不说一下这个提交方式转换！！真的坑。。改了好长时间！！一定要记得加响应头
            httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript; charset=UTF-8");// 设置响应头
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", scene); // 你要放的内容
            paramJson.put("path", "pages/home/HomePage");
            paramJson.put("width", 430); // 宽度
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            //创建一个空文件
            OutputStream os = new FileOutputStream(new File("D:\\test.jpg"));
            //ByteArrayOutputStream os = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1)
            {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            bis.close();
            // 上传云储存
            //InputStream is = new ByteArrayInputStream(os.toByteArray());
            //retMap = UploadUtils.upload(is);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testX(){
        String accessToken = tencentMiniProApiService.getAuthAccessToken("wx968357120a739302", "2838a72831e5fd9a8adbe828a74c4d53");
        System.out.println(accessToken);

        String bcode = tencentMiniProApiService.getUnlimitedWxBCode(accessToken, "wx968357120a739302#121122112", "pages/reportMgr/HealthReportPage", 430, true, null, false);
        String acode = tencentMiniProApiService.getWxACode(accessToken, "pages/reportMgr/HealthReportPage", 430, true, null, false);
        System.out.println(bcode);
        System.out.println(acode);
//        System.out.println(unlimited);


    }

}
