package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity {

	Context context;
	Intent intent;
	String allChecklistsJSONString;
	String allStepsJSONString;
	ArrayList<String> allStepsJSONStringArray;
	ArrayList<String[]> checklistsArray;
	ArrayList<String> checklistsIdArray;
	ArrayList<String[]> stepsArray;
	ArrayList<String[]> allStepsArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_splash);
		context = getApplicationContext();

		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}

	private class IntentLauncher extends Thread {
		
		@Override
		public void run() {
			
			HTTPGetRequest getRequest = new HTTPGetRequest();
			HTTPPostRequest postRequest = new HTTPPostRequest();
			try {
				
				// Test POST to server
				postRequest.multipartPost();
				
				JSONWriter writer = new JSONWriter(context);
				JSONReader reader = new JSONReader(context);
				
				// Creates array of all checklists based on GroupId
				allChecklistsJSONString = getRequest.getChecklists(1);
				
				writer.writeToInternal(allChecklistsJSONString);
				reader.readFromInternal(writer.filename);
				
				checklistsArray = reader.getChecklistsArray();
				checklistsIdArray = reader.getChecklistIdsArray();
				
				// Creates array of all steps
				allStepsJSONStringArray = new ArrayList<String>();
				allStepsArray = new ArrayList<String[]>();
				
				for (int i = 0; i < checklistsIdArray.size(); i++) {
					allStepsJSONString = getRequest.getSteps(Integer.parseInt(checklistsIdArray.get(i)));
					allStepsJSONStringArray.add(allStepsJSONString);
					
				}
				
				for (int i = 0; i < allStepsJSONStringArray.size(); i++) {
					writer.writeToInternal(allStepsJSONStringArray.get(i));
					reader.readFromInternal(writer.filename); 
					stepsArray = reader.getStepsArray();
					for (int s = 0; s < stepsArray.size(); s++) {
						allStepsArray.add(stepsArray.get(s));
					}
				}
				
				Log.v("# of steps", Integer.toString(allStepsArray.size()));
				
				for (int i = 0; i < allStepsArray.size(); i++) {
					Log.v("Checklist Steps", Arrays.toString(allStepsArray.get(i)));
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Pass checklists and steps arrays to main menu
			intent = new Intent(context, MainMenuActivity.class);
			intent.putExtra("checklists", checklistsArray);
			intent.putExtra("steps", stepsArray);
			
			// Start main menu activity
			SplashActivity.this.startActivity(intent);
			SplashActivity.this.finish();
		}
	}
	
}
