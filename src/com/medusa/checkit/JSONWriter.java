package com.medusa.checkit;

import android.content.Context;
import android.util.Log;

import java.io.*;

public class JSONWriter {
	
	static final String FILENAME = "temp.json";
	
	Context context;
	
	public JSONWriter(Context context) {
		this.context = context;
	}
	
	public void writeToInternal(String data) throws IOException {
		FileOutputStream fos = null;
		
		try {
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			Log.v("writeToJSON", FILENAME + " has been written");
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
