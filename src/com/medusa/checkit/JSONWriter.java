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
		DataInputStream dis = null;
		try	{
			dis = new DataInputStream(context.openFileInput(filename));
			String text = dis.readUTF();
			Log.v("readFromJSON", text);
		} catch (EOFException e) {
			
		}
		dis.close();
	}
	
}
