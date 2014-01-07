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
	ArrayList<String[]> checklistsArray;
	ArrayList<String> checklistsIdArray;
	ArrayList<String> allStepsJSONStringArray;
	ArrayList<String[]> stepsArray;
	
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
				
				// Retrieves JSON String
				allChecklistsJSONString = getRequest.getChecklists(1);
				
				// Creates array of all checklists
				Log.v("Checklists Array", "writing to JSON");
				JSONWriter checklistWriter = new JSONWriter(context, allChecklistsJSONString);
				JSONReader checklistReader = new JSONReader(context, checklistWriter.filename);
				
				checklistWriter.writeToInternal();
				checklistReader.readFromInternal();
				
				checklistsArray = checklistReader.getChecklistsArray();
				checklistsIdArray = checklistReader.getChecklistIdsArray();
				
				// Creates array of all steps
				Log.v("Steps Array", "writing to JSON");
				JSONWriter stepsWriter = new JSONWriter(context, allStepsJSONString);
				JSONReader stepsReader = new JSONReader(context, stepsWriter.filename);
				
				stepsWriter.writeToInternal();
				stepsReader.readFromInternal();
				
				stepsArray = stepsReader.getStepsArray();
				
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
