package com.medusa.checkit;

import java.net.*;
import java.io.*;

public class HTTPRequest {
	String baseURL;
	String baseGroupURL;
	String baseChecklistURL;
	String groupId;
	String checklistId;
	String listOfChecklistsURL;
	String checklistStepsURL;
	
	public HTTPRequest(int groupId, int checklistId) {
		this.baseURL = "http://dev.darthyogurt.com:8000/checklist/";
		this.baseGroupURL = "groupid/";
		this.baseChecklistURL = "checklistid/";
		this.groupId = Integer.toString(groupId);
		this.checklistId = Integer.toString(checklistId);
		this.listOfChecklistsURL = baseURL + baseGroupURL + groupId;
		this.checklistStepsURL = baseURL + baseChecklistURL + checklistId;
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
