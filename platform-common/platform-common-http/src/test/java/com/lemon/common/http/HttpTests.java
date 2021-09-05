package com.lemon.common.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lemon.common.http.HttpApplication;
import com.lemon.common.http.client.HttpClientService;
import com.lemon.common.http.client.HttpResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpApplication.class)
public class HttpTests {

	private String uri = "https://login.ti.com/as/authorization.oauth2?response_type=code&scope=openid%20email%20profile&client_id=DCIT_ALL_WWW-PROD&state=Wg6Dgsl1z3sEAcGD6STNgHfnia0&redirect_uri=https%3A%2F%2Fwww.ti.com%2Foidc%2Fredirect_uri%2F&nonce=F9n5sOJXOZLnWmEiSuRt0kE0Nyu2AerLj0HuYIhlJ_w&response_mode=form_post";
	
	@Autowired
	HttpClientService httpClientService;
	
	 @Test
	public void sayHello() throws IOException, URISyntaxException{
		
		httpClientService.post("192.168.9.201:18070", null);
	}

	@Test
	public void get() throws IOException, URISyntaxException {
		HttpResponseWrapper responseWrapper = httpClientService.get("http://www.vhcloud.net/EcgView.aspx?id=368497", null);
		System.out.println(responseWrapper.getResponseBody());

	}

	@Test
	public void getTest() throws IOException, URISyntaxException {
		Map<String, Object> params = new HashMap<String, Object>();
		HttpResponseWrapper responseWrapper = httpClientService.get("https://www.ti.com/search/coveo/newToken",params);
		System.out.println(responseWrapper.getResponseBody());
	}

	/**
	 * 2、登陆请求
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	//@Test
	public void postTest(String action) throws IOException, URISyntaxException {
	 	String URL= "https://login.ti.com/as/"+action+"/resume/as/authorization.ping";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pf.username","854757115@qq.com");
		params.put("pf.pass","Xjc.1123");
		params.put("pf.adapterId","IDPAdapterHTMLFormCIDStandard");
		HttpResponseWrapper wrapper = httpClientService.post(URL,params);
		log.info("响应：{}",wrapper.getResponseBody());
	}

	/**
	 * 1、获取登陆页面
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void postGetLoginPage() throws IOException, URISyntaxException {
	 	String url = "https://login.ti.com/as/authorization.oauth2";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("response_type","code");
		params.put("scope","openid email profile");
		params.put("client_id","DCIT_ALL_WWW-PROD");
		params.put("state","uwQOHVYyBT9owJSoBF30tedfJOM");
		params.put("redirect_uri","https://www.ti.com/oidc/redirect_uri/");
		params.put("nonce","dS364coOCp9kOJWpT4_QyMQaMKOIVosI_QN4fzqMADI");
		params.put("response_mode","form_post");
		HttpResponseWrapper wrapper = httpClientService.post(url,params);
//		String data = wrapper.getResponseBody();
//		int a = data.indexOf("action', '/as/");
//		log.info("action：{}",wrapper.getResponseBody().substring(7613,7618));
//		log.info("原始响应：{}",wrapper.getResponseBody());
		postTest(wrapper.getResponseBody().substring(7613,7618));
	}



	@Test
	public void testScrapy() throws ClientProtocolException, IOException, URISyntaxException {
		String url = "http://www.vhcloud.net/EcgView.aspx?id=368497";
		Map<String, String> params = new HashMap<String, String>();
		CloseableHttpClient httpclient = HttpClients.custom().build();	
        try {
        	
        	/*URI uri = new URIBuilder()
        	        .setScheme("http")
        	        .setHost("http://www.vhcloud.net")
        	        .setPath("/EcgView.aspx")
        	        .setParameter("id", "368497")
        	        .build(); */
        	HttpGet httpGet = new HttpGet(url);
        	
        	RequestConfig config = RequestConfig.custom()
        			.setConnectTimeout(10000)
        			.setSocketTimeout(10000)
        			.setConnectionRequestTimeout(10000)
        			.build();

        	
        	httpGet.setConfig(config);
        	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        	Iterator<String> iter = params.keySet().iterator();
        	while (iter.hasNext()) {
        		String key = iter.next();
        		String val = params.get(key);
        		nvps.add(new BasicNameValuePair(key, val));
        	}

        	// httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        	
        	
        	Header headers[] = {
        			//new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"),
        			//new BasicHeader("Accept-Encoding", "gzip, deflate, sdch"),
        			//new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"),
        			new BasicHeader("Cookie", "ASP.NET_SessionId=scitdk0nbzqz0cqfricndhah; .ASPXAUTH=D7F4B4E756822CD3783255AF7EF2F05F75A2E4C69D32CE278F76155198E5DED9D956BAFB8480E14754B1BC2B99B3159A738F9F6863F9C0649F5B0ECB5AC3278C78344F2952EED63E8E984E619A87E48561AA7990B16DCC5A7097AAF7728C457B230A0676E32B2BA73F0017311B8B768FF04F4583DC39B5308305D9038D489C5B; lastviewed=368497; lead_layout=2"),
        			//new BasicHeader("Host", "www.vhcloud.net"),
        			//new BasicHeader("Referer", "http://www.vhcloud.net/Account/Login.aspx?ReturnUrl=%2fEcgView.aspx%3fid%3d368497&id=368497"),
        			new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36")
        	};
        	httpGet.setHeaders(headers);

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
            HttpResponseWrapper rsp = httpclient.execute(httpGet, responseHandler);
            System.out.println(rsp.getResponseBody());
			System.out.println(rsp.getStatusCode());
        } finally {
            httpclient.close();
        }
	}
	
	
}
