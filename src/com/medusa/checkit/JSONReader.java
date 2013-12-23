package com.medusa.checkit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

//Reads JSON file and puts all info into ArrayList<String[]>
public class JSONReader {
	Context context;
	ArrayList<String[]> data;
	
	public JSONReader(Context context) {
		this.context = context;
	}
	
	void readJson() {
		InputStream inputStream = context.getResources().openRawResource(R.raw.list_of_checklists);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        try {
        	// Parse the data into JSONObject to get original data in form of JSON
            JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());

            JSONArray jArray = jObject.getJSONArray("listOfChecklists");
            String id="";
            String checklistName ="";

            data = new ArrayList<String[]>();
            for (int i = 0; i < jArray.length(); i++) {
                id = jArray.getJSONObject(i).getString("id");
                checklistName = jArray.getJSONObject(i).getString("checklistName");
                
                Log.v("ID", id);
                Log.v("Checklist Name", checklistName);
                data.add(new String[] {id, checklistName});
                System.out.println(Arrays.toString(data.get(i)));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
