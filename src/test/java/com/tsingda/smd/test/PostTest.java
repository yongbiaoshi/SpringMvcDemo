package com.tsingda.smd.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


public class PostTest {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost("http://192.168.2.123:3000/tv/errorlog");
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("deviceUserType", "1"));
        parameters.add(new BasicNameValuePair("deviceId", "f6655f5e-f70c-11e5-9ce9-5e5517507c67"));
        parameters.add(new BasicNameValuePair("agentId", "56fced45cb295d54c26d9125"));
        parameters.add(new BasicNameValuePair("errorCode", "1001"));
        parameters.add(new BasicNameValuePair("errorDetail", "出错了，不知道原因！"));
        parameters.add(new BasicNameValuePair("errorTime", "2016-04-08 10:33:22"));
        
        post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
        
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = clientBuilder.build();
        ResponseHandler<String> handler = new BasicResponseHandler();
        String response = client.execute(post, handler);
        client.close();
        System.out.println(response);
    }
}
