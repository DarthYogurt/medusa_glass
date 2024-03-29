package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
	
	Intent newChecklistIntent;
	String allChecklistsJSONString;
	ArrayList<String[]> checklistsArray;
	ArrayList<String[]> stepsArray;

	@SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_menu);
        
        checklistsArray = (ArrayList<String[]>) this.getIntent().getSerializableExtra("checklists");
        stepsArray = (ArrayList<String[]>) this.getIntent().getSerializableExtra("steps");
        
		newChecklistIntent = new Intent(this, NewChecklistActivity.class);
		newChecklistIntent.putExtra("checklists", checklistsArray);
		newChecklistIntent.putExtra("steps", stepsArray);
        
    }
	
	@Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    } 
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
        	case R.id.newChecklist:
        		startActivity(newChecklistIntent);
        		return true;
        	case R.id.continueChecklist:
        		startActivity(new Intent(this, ContinueChecklistActivity.class));
        		return true;
            case R.id.stop:
                stopService(new Intent(this, ChecklistService.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
    public void onOptionsMenuClosed(Menu menu) {
        // Nothing else to do, closing the Activity.
        finish();
    }
	
//	private class BackgroundTask extends AsyncTask<Void, Void, Void> {
//		
//		protected Void doInBackground(Void... params) {
//			HTTPGetRequest getRequest = new HTTPGetRequest();
//			HTTPPostRequest postRequest = new HTTPPostRequest();
//			try {
//				
//				allChecklistsJSONString = getRequest.getChecklists(1);
//				
//				postRequest.multipartPost();
//				
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		protected void onPostExecute(Void result) {
//			try {
//				Log.v("onPostExecute", "writing to JSON");
//				JSONWriter writer = new JSONWriter(context, allChecklistsJSONString);
//				JSONReader reader = new JSONReader(context, writer.filename);
//				
//				writer.writeToInternal();
//				reader.readFromInternal();
//				
//				checklistsArray = reader.getChecklistsArray();
//				newChecklistIntent = new Intent(context, NewChecklistActivity.class);
//				newChecklistIntent.putExtra("checklists", checklistsArray);
//				
//				bgTaskRunning.setVisibility(View.GONE);
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return;
//		}
//	}
	
}
