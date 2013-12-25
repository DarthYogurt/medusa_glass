package com.medusa.checkit;

import android.content.Context;
import android.util.Log;

import java.io.*;

public class JSONWriter {
	Context context;
	String data;
	
	public JSONWriter(Context context, String data) {
		this.context = context;
		this.data = data;
	}
	
	public void writeToJSON() throws IOException {
		String filename = "test.json";
		FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
		fos.write(data.getBytes());
		fos.close();
	}
	
}
