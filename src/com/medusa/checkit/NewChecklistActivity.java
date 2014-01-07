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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class NewChecklistActivity extends Activity {
	
	private GestureDetector mGestureDetector;
	Context context;
	BackgroundTask bgTask;
	String allStepsJSONString;
	ArrayList<String[]> allStepsArray;
	ArrayList<String[]> checklistsArray;
	ArrayList<String[]> stepsArray;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_checklist);
		context = getApplicationContext();
//		mGestureDetector = createGestureDetector(this);
		
		checklistsArray = (ArrayList<String[]>) this.getIntent().getSerializableExtra("checklists");
		
		bgTask = new BackgroundTask();
		bgTask.execute();
	}
	
	private class BackgroundTask extends AsyncTask<Void, Void, Void> {
		
		protected Void doInBackground(Void... params) {
			HTTPGetRequest getRequest = new HTTPGetRequest();
			
			try {
				
				allStepsJSONString = getRequest.getSteps(2);
				
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
				JSONWriter writer = new JSONWriter(context, allStepsJSONString);
				JSONReader reader = new JSONReader(context, writer.filename);
				
				writer.writeToInternal();
				reader.readFromInternal();
				
				stepsArray = reader.getStepsArray();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	
	@Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }
	
	// Use this instead of onCreateOptionsMenu if menu is dynamic
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		String[] menuItem;
		
		menu.clear();
		for (int i = 0; i < checklistsArray.size(); i++) {
			menuItem = checklistsArray.get(i);
			menu.add(0, i, Menu.NONE, menuItem[1]);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
        	case 0:
        		startActivity(new Intent(this, ContinueChecklistActivity.class));
        		return true;
        	case 1:
        		startActivity(new Intent(this, ContinueChecklistActivity.class));
        		return true;
            case 2:
            	startActivity(new Intent(this, ContinueChecklistActivity.class));
                return true;
        	case 3:
        		startActivity(new Intent(this, ContinueChecklistActivity.class));
        		return true;
            case 4:
            	startActivity(new Intent(this, ContinueChecklistActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
//  === GESTURE DETECTION ===	
//	private GestureDetector createGestureDetector(Context context) {
//	    GestureDetector gestureDetector = new GestureDetector(context);
//	    
//        //Create a base listener for generic gestures
//        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
//            @Override
//            public boolean onGesture(Gesture gesture) {
//                if (gesture == Gesture.TAP) {
//                	// do something on one finger tap
//                    return true;
//                } else if (gesture == Gesture.TWO_TAP) {
//                    // do something on two finger tap
//                    return true;
//                } else if (gesture == Gesture.SWIPE_RIGHT) {
//                    // do something on right (forward) swipe
//                    return true;
//                } else if (gesture == Gesture.SWIPE_LEFT) {
//                    // do something on left (backwards) swipe
//                    return true;
//                }
//                return false;
//            }
//        });
//        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
//            @Override
//            public void onFingerCountChanged(int previousCount, int currentCount) {
//              // do something on finger count changes
//            }
//        });
//        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
//            @Override
//            public boolean onScroll(float displacement, float delta, float velocity) {
//                // do something on scrolling
//            	return true;
//            }
//        });
//        return gestureDetector;
//    }
//	
//    // Send generic motion events to the gesture detector
//    @Override
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        if (mGestureDetector != null) {
//            return mGestureDetector.onMotionEvent(event);
//        }
//        return false;
//    }

}
