package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {
	Context context;
	String JSONString;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        
        new BackgroundTask().execute();
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
        		startActivity(new Intent(this, NewChecklistActivity.class));
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
	
	private class BackgroundTask extends AsyncTask<Void, Void, Void> {
		
		protected Void doInBackground(Void... params) {
			HTTPGetRequest getRequest = new HTTPGetRequest();
			HTTPSendRequest sendRequest = new HTTPSendRequest();
			try {
				JSONString = getRequest.getChecklists(1);
				
				sendRequest.sendPost();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			try {
				Log.v("onPostExecute", "writing to JSON");
				JSONWriter writer = new JSONWriter(context, JSONString);
				JSONReader reader = new JSONReader(context, writer.filename);
				writer.writeToInternal();
				reader.readFromInternal();
				reader.getChecklistsArray();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
}
