package com.medusa.checkit;

import android.content.Context;
import android.util.Log;

import java.io.*;

public class JSONWriter {
	Context context;
	String data;
	String filename;
	
	public JSONWriter(Context context, String data) {
		this.context = context;
		this.data = data;
		this.filename = "temp.json";
	}
	
	public void writeToInternal() throws IOException {
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
	
}
