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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpApplication.class)
public class HttpTests {
	
	@Autowired
	HttpClientService httpClientService;
	
	 @Test
	public void sayHello() throws IOException, URISyntaxException{
		
		httpClientService.post("192.168.9.201:18070", null);
	}

	@Test
	public void get() throws IOException, URISyntaxException {
		com.lemon.common.http.client.HttpResponseWrapper responseWrapper = httpClientService.get("http://www.vhcloud.net/EcgView.aspx?id=368497", null);
		System.out.println(responseWrapper.getResponseBody());

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
