package com.medusa.checkit;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class JSONWriter {
	
	static final String FILENAME = "temp.json";
	static final String CHECKLIST_FILENAME = "new_checklist.json";
	
	FileOutputStream fos;
	JsonWriter checklistWriter;
	
	
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
	
	public void startNewChecklist(int checklistId) throws IOException {
		
//		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "new_checklist.json");
		
		try {
			fos = context.openFileOutput(CHECKLIST_FILENAME, Context.MODE_PRIVATE);
			checklistWriter = new JsonWriter(new OutputStreamWriter(fos, "UTF-8"));
			Log.v("new file", CHECKLIST_FILENAME + " has been created");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			checklistWriter.beginObject();
			checklistWriter.name("userId").value(1);
			checklistWriter.name("groupId").value(1);
			checklistWriter.name("checklistId").value(checklistId);
			checklistWriter.name("steps");
			checklistWriter.beginArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void finishNewChecklist() throws IOException {
		try {
			checklistWriter.endArray();
			checklistWriter.endObject();
			checklistWriter.close();
	    } catch (IOException e) { e.printStackTrace(); }
		try { fos.close(); } 
		catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public void writeStepBoolean(int stepId, boolean result) throws IOException {
		try {
			checklistWriter.beginObject();
			checklistWriter.name("stepId").value(stepId);
			checklistWriter.name("stepType").value("bool");
			if (result == true) { checklistWriter.name("value").value("true"); }
			else { checklistWriter.name("value").value("false"); }
			checklistWriter.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeStepDouble(int stepId, double result) throws IOException {
		try {
			checklistWriter.beginObject();
			checklistWriter.name("stepId").value(2);
			checklistWriter.name("stepType").value("double");
			checklistWriter.name("value").value("2");
			checklistWriter.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeStepText(int stepId, String result) throws IOException {
		try {
			checklistWriter.beginObject();
			checklistWriter.name("stepId").value(3);
			checklistWriter.name("stepType").value("text");
			checklistWriter.name("value").value("Monkey is caged");
			checklistWriter.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
