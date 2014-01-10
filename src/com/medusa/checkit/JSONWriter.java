package com.medusa.checkit;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class JSONWriter {
	
	static final String FILENAME = "temp.json";
	static final String CHECKLIST_NAME = "new_checklist.json";
	
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
	
	public void newChecklist() throws IOException {
		
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "new_checklist.json");
		file.createNewFile();
		JsonWriter writer = null;
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer.beginObject();
			writer.name("userId").value(1);
			writer.name("groupId").value(1);
			writer.name("checklistId").value(1);
			writer.name("steps");
			writer.beginArray();
			writer.beginObject();
			writer.name("stepId").value(1);
			writer.name("stepType").value("bool");
			writer.name("value").value("true");
			writer.endObject();
			writer.beginObject();
			writer.name("stepId").value(2);
			writer.name("stepType").value("double");
			writer.name("value").value("2");
			writer.endObject();
			writer.beginObject();
			writer.name("stepId").value(3);
			writer.name("stepType").value("text");
			writer.name("value").value("Monkey is caged");
			writer.endObject();
			writer.beginObject();
			writer.name("stepId").value(4);
			writer.name("stepType").value("bool");
			writer.name("value").value("false");
			writer.endObject();
			writer.endArray();
			writer.endObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
		        writer.close();
		    } catch (IOException e) {

		    }
		}
	    
	}
	
}
