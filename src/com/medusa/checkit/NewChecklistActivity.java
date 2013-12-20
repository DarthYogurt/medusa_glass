package com.medusa.checkit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

public class NewChecklistActivity extends Activity {
	
	private GestureDetector mGestureDetector;
	ArrayList<String[]> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_checklist);
		mGestureDetector = createGestureDetector(this);
		readJson();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_checklist, menu);
		return true;
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

    // Reads JSON file and puts all info into ArrayList<String[]>
	void readJson() {
		InputStream inputStream = getResources().openRawResource(R.raw.list_of_checklists);

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
        Log.v("Text Data", byteArrayOutputStream.toString());
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

                data.add(new String[] {id, checklistName});
            }

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
