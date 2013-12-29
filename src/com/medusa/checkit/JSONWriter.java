package com.medusa.checkit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.*;

public class JSONWriter {
	Context context;
	String data;
	String filename;
	
	public JSONWriter(Context context, String data) {
		this.context = context;
		this.data = data;
	}
	
	public void writeToJSON() throws IOException {
		filename = "test.json";
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			Log.v("writeToJSON", filename + " has been written");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void readFromJSON() throws IOException {
		BufferedReader br = null;
		
		try {
			FileInputStream fis = context.openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			String text = br.readLine();
			Log.v("readFromJSON", text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
