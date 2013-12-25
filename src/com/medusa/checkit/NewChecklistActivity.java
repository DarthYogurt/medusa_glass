package com.medusa.checkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

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
	
	private GestureDetector mGestureDetector;
	String data;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_checklist);
		mGestureDetector = createGestureDetector(this);
		
//		JSONReader jsonReader = new JSONReader(this.getApplicationContext());
//		jsonReader.readJson();
//		jsonReader.getData();
		
		new BackgroundTask().execute();
		

	}
	
	public void writeToJSON() throws IOException {
		String filename = "test.json";
		FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
		fos.write(data.getBytes());
		fos.close();
	}
	
	public class BackgroundTask extends AsyncTask<Void, Void, Void> {
		
		protected Void doInBackground(Void... params) {
			HTTPRequest request = new HTTPRequest(1, 2);
			
			try {
				data = request.GetRequest();
				Log.v("doInBackground", data);
//				request.writeToJSON();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute() {
			try {
				Log.v("onPostExecute", "1");
				writeToJSON();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.new_checklist, menu);
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
