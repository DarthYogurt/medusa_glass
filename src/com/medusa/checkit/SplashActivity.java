package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

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
				
				allChecklistsJSONString = getRequest.getChecklists(1);
				
				postRequest.multipartPost();
				
				Log.v("onPostExecute", "writing to JSON");
				JSONWriter writer = new JSONWriter(context, allChecklistsJSONString);
				JSONReader reader = new JSONReader(context, writer.filename);
				
				writer.writeToInternal();
				reader.readFromInternal();
				
				checklistsArray = reader.getChecklistsArray();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Start main menu activity and pass in checklists and steps data
			intent = new Intent(context, MainMenuActivity.class);
			intent.putExtra("checklists", checklistsArray);
			SplashActivity.this.startActivity(intent);
			SplashActivity.this.finish();
		}
	}
	
}
