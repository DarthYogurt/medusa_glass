package com.medusa.checkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class HTTPSendRequest {
	String url = "http://dev.darthyogurt.com:8000/";
	
	public void sendPost() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("test_string", "test"));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		httpClient.execute(post);
		Log.v("sendPost", "POST sent successfully");

	}
	
}
