package com.medusa.checkit;

import java.io.File;
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
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.os.Environment;
import android.util.Log;

public class HTTPPostRequest {
	static final File EXTERNALSTORAGE = Environment.getExternalStorageDirectory();
	String url = "http://dev.darthyogurt.com:8000/testPost/";
	
	// For sending files using MultipartEntity
	public void multipartPost() throws ClientProtocolException, IOException {
		File image = new File(EXTERNALSTORAGE + "/Pictures/sample.jpg");
		File json = new File(EXTERNALSTORAGE + "/Pictures/checklist1.json");
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("enctype", "multipart/form-data");

		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
		multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		multipartEntity.addPart("sampleImage", new FileBody(image));
		multipartEntity.addPart("sampleJSON", new FileBody(json));
		post.setEntity(multipartEntity.build());
		
		HttpResponse response = client.execute(post);
		String responseBody = EntityUtils.toString(response.getEntity());
		Log.v("multiPartPost HTTP Response", responseBody);
	}
	
//	public void textPost() throws ClientProtocolException, IOException {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		
//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		nameValuePairs.add(new BasicNameValuePair("Test String", "test"));
//		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//		Log.v("sendPost", "POST sent successfully");
//		
//		// Response from sending HTTP POST
//		HttpResponse response = httpClient.execute(httpPost);
//		String responseBody = EntityUtils.toString(response.getEntity());
//		Log.v("HTTP Response", responseBody);
//	}
//	
//	public void imagePost() throws ClientProtocolException, IOException {
//		File image = new File(EXTERNALSTORAGE + "/Pictures/sample.jpg");
//		
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		
//		httpPost.setEntity(new FileEntity(image, "enctype=multipart/form-data"));
//		Log.v("sendPost", "POST sent successfully");
//		
//		// Response from sending HTTP POST
//		HttpResponse response = httpClient.execute(httpPost);
//		String responseBody = EntityUtils.toString(response.getEntity());
//		Log.v("imagePost HTTP Response", responseBody);
//	}
//	
//	public void jsonPost() throws ClientProtocolException, IOException {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		
//		httpPost.setEntity(new FileEntity(new File(EXTERNALSTORAGE + "/Pictures/checklist1.json"), "application/json"));
//		Log.v("sendPost", "POST sent successfully");
//		
//		// Response from sending HTTP POST
//		HttpResponse response = httpClient.execute(httpPost);
//		String responseBody = EntityUtils.toString(response.getEntity());
//		Log.v("jsonPost HTTP Response", responseBody);
//	}
			
}
