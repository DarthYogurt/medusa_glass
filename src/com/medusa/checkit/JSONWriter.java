package com.medusa.checkit;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class JSONWriter {
	ArrayList<String[]> data;
	
	public void writeToJSON(String httpString) throws Exception {
//		String FILENAME = "test.json";
//		String string = "hello world!";
//
//		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//		fos.write(string.getBytes());
//		fos.close();
		
		
//		try {
//        	// Parse the data into JSONObject to get original data in form of JSON
//            JSONObject jObject = new JSONObject();
//
//            JSONArray jArray = jObject.getJSONArray(httpString);
//            String id="";
//            String checklistName ="";
//
//            data = new ArrayList<String[]>();
//            for (int i = 0; i < jArray.length(); i++) {
//                id = jArray.getJSONObject(i).getString("id");
//                checklistName = jArray.getJSONObject(i).getString("name");
//                
//                data.add(new String[] {id, checklistName});
//                System.out.println(data.get(i));
//            }
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }
		
	}
	
}
