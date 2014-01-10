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
	static final String URL = "http://dev.darthyogurt.com:8000/upload/";
	
	// For sending files using MultipartEntity
	public void multipartPost() throws ClientProtocolException, IOException {
		File json = new File(EXTERNALSTORAGE + "/Pictures/test.json");
		File image = new File(EXTERNALSTORAGE + "/Pictures/test.jpg");
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);
		post.setHeader("enctype", "multipart/form-data");

		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
		multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		multipartEntity.addPart("data", new FileBody(json));
		multipartEntity.addPart("sampleImage", new FileBody(image));
		post.setEntity(multipartEntity.build());
		
		HttpResponse response = client.execute(post);
		String responseBody = EntityUtils.toString(response.getEntity());
		Log.v("multiPartPost HTTP Response", responseBody);
	}
			
}
