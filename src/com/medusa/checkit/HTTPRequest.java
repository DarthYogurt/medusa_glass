package com.medusa.checkit;

import java.net.*;
import java.io.*;

public class HTTPRequest {
	String baseURL;
	String baseGroupId;
	String baseChecklistId;
	String groupId;
	String checklistId;
	String listOfChecklistsURL;
	String checklistStepsURL;
	
	public HTTPRequest(int groupId, int checklistId) {
		this.baseURL = "http://dev.darthyogurt.com:8000/checklist/";
		this.baseGroupId = "groupid/";
		this.baseChecklistId = "checklistid/";
		this.groupId = Integer.toString(groupId);
		this.checklistId = Integer.toString(checklistId);
		this.listOfChecklistsURL = baseURL + baseGroupId + groupId;
		this.checklistStepsURL = baseURL + baseChecklistId + checklistId;
	}
	
	public void GetRequest() throws MalformedURLException, IOException {
		String charset = "UTF-8";
		URLConnection connection = new URL(checklistStepsURL).openConnection();
		InputStream response = connection.getInputStream();
		String contentType = connection.getHeaderField("Content-Type");
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
					System.out.println(line);
				}
			}
			finally {
				try { reader.close(); } catch (IOException logOrIgnore) {}
			}
		}
	}
}