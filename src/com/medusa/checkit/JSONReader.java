package com.medusa.checkit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

//Reads JSON file and puts all info into ArrayList<String[]>
public class JSONReader {
	Context context;
	String filename;
	String jsonString;
	ArrayList<String[]> checklistsArray;
	ArrayList<String[]> stepsArray;
	
	public JSONReader(Context context, String filename) {
		this.context = context;
		this.filename = filename;
	}
	
	public void readFromInternal() throws IOException {
		BufferedReader br = null;
		
		try {
			FileInputStream fis = context.openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			jsonString = br.readLine();
			Log.v("readFromJSON", jsonString);
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
	
	public void getChecklistsArray() {
		try {
            JSONObject jObject = new JSONObject(jsonString);
            JSONArray jArray = jObject.getJSONArray("checklist");
            String id = null;
            String checklistName = null;

            checklistsArray = new ArrayList<String[]>();
            for (int i = 0; i < jArray.length(); i++) {
                id = jArray.getJSONObject(i).getString("id");
                checklistName = jArray.getJSONObject(i).getString("name");
                checklistsArray.add(new String[] {id, checklistName});
            }
            
            for (int i = 0; i < checklistsArray.size(); i++) {
    			Log.v("Checklists Array", Arrays.toString(checklistsArray.get(i)));
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public int getNumOfChecklists() {
		return checklistsArray.size();
	}
	
	public void getStepsArray() {
		try {
            JSONObject jObject = new JSONObject(jsonString);
            JSONArray jArray = jObject.getJSONArray("steps");
            String stepNumber = null;
            String stepName = null;
            String stepType = null;

            stepsArray = new ArrayList<String[]>();
            for (int i = 0; i < jArray.length(); i++) {
                stepNumber = jArray.getJSONObject(i).getString("stepNumber");
                stepName = jArray.getJSONObject(i).getString("name");
                stepType = jArray.getJSONObject(i).getString("stepType");
                stepsArray.add(new String[] {stepNumber, stepName, stepType});
            }
            
            for (int i = 0; i < stepsArray.size(); i++) {
    			Log.v("Steps Array", Arrays.toString(stepsArray.get(i)));
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public int getNumOfSteps() {
		return stepsArray.size();
	}

}
