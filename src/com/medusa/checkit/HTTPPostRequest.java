package com.medusa.checkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HTTPPostRequest {
	String url = "http://dev.darthyogurt.com:8000/testPost/";
	
	public void sendPost() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("test_string", "test"));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		Log.v("sendPost", "POST sent successfully");
		
		HttpResponse response = httpClient.execute(httpPost);
		String responseBody = EntityUtils.toString(response.getEntity());
		Log.v("Error Msg", responseBody);

		
//		HttpClient httpClient = new DefaultHttpClient();
//	    HttpContext localContext = new BasicHttpContext();
//	    HttpPost httpPost = new HttpPost(url);
//
//	    try {
//	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//	        for(int index=0; index < nameValuePairs.size(); index++) {
//	            if(nameValuePairs.get(index).getName().equalsIgnoreCase("image")) {
//	                // If the key equals to "image", we use FileBody to transfer the data
//	                entity.addPart(nameValuePairs.get(index).getName(), new FileBody(new File (nameValuePairs.get(index).getValue())));
//	            } else {
//	                // Normal string data
//	                entity.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue()));
//	            }
//	        }
//
//	        httpPost.setEntity(entity);
//
//	        HttpResponse response = httpClient.execute(httpPost, localContext);
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
		
	}
	
}
