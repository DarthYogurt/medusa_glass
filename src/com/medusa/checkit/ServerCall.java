package com.medusa.checkit;

import java.net.*;
import java.io.*;

import android.os.AsyncTask;
import android.util.Log;

public class ServerCall {
	String baseURL;
	String baseGroupId;
	String groupId;
	String checklistURL;
	
	public ServerCall(int groupId) {
		this.baseURL = "http://dev.darthyogurt.com/";
		this.baseGroupId = "checklist/groupid/";
		this.groupId = Integer.toString(groupId);
		this.checklistURL = baseURL + baseGroupId + groupId;
	}
	
	public void GetRequest() throws MalformedURLException, IOException {
		String charset = "UTF-8";
		URLConnection connection = new URL(checklistURL).openConnection();
		InputStream response = connection.getInputStream();
		String contentType = connection.getHeaderField("Content-Type");
		Log.v("content-type", contentType);
		for (String param : contentType.replace(" ", "").split(";")) {
			if (param.startsWith("charset=")) {
				charset = param.split("=", 2)[1];
				break;
			}
		}
		
		if (charset != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
			try {
				for (String line; (line = reader.readLine()) != null;) {
					System.out.println(checklistURL);
					System.out.println(line);
				}
			}
				finally {
					try { reader.close(); } catch (IOException logOrIgnore) {}
				}
			}
		else {
			System.out.println("no charset");
		}
	}
}
