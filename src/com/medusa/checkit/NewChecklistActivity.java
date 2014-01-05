package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

public class NewChecklistActivity extends Activity {
	Context context;
	private GestureDetector mGestureDetector;
	String JSONString;
	Bundle bundle;
	ArrayList<String[]> checklistsArray;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_checklist);
		context = getApplicationContext();
		mGestureDetector = createGestureDetector(this);
		
		checklistsArray = (ArrayList<String[]>) this.getIntent().getSerializableExtra("checklists");
		
		for (int i = 0; i < checklistsArray.size(); i++) {
			Log.v("Checklists Array", Arrays.toString(checklistsArray.get(i)));
		}
		
		
//		new BackgroundTask().execute();
	}
	
//	private class BackgroundTask extends AsyncTask<Void, Void, Void> {
//		
//		protected Void doInBackground(Void... params) {
//			HTTPGetRequest getRequest = new HTTPGetRequest();
//			HTTPSendRequest sendRequest = new HTTPSendRequest();
//			try {
//				JSONString = getRequest.getChecklists(1);
//				
//				sendRequest.sendPost();
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
//				JSONWriter writer = new JSONWriter(context, JSONString);
//				JSONReader reader = new JSONReader(context, writer.filename);
//				writer.writeToInternal();
//				reader.readFromInternal();
//				reader.getChecklistsArray();
//				
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return;
//		}
//	}
	
	@Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }
	
	// Use this instead of onCreateOptionsMenu if menu is dynamic
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		return super.onPrepareOptionsMenu(menu);
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	private GestureDetector createGestureDetector(Context context) {
	    GestureDetector gestureDetector = new GestureDetector(context);
	    
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                	// do something on one finger tap
                    return true;
                } else if (gesture == Gesture.TWO_TAP) {
                    // do something on two finger tap
                    return true;
                } else if (gesture == Gesture.SWIPE_RIGHT) {
                    // do something on right (forward) swipe
                    return true;
                } else if (gesture == Gesture.SWIPE_LEFT) {
                    // do something on left (backwards) swipe
                    return true;
                }
                return false;
            }
        });
        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
            @Override
            public void onFingerCountChanged(int previousCount, int currentCount) {
              // do something on finger count changes
            }
        });
        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
            @Override
            public boolean onScroll(float displacement, float delta, float velocity) {
                // do something on scrolling
            	return true;
            }
        });
        return gestureDetector;
    }
	
    // Send generic motion events to the gesture detector
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }

}
