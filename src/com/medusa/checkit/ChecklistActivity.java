package com.medusa.checkit;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ChecklistActivity extends Activity {
	ArrayList<String[]> steps;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checklist);
//		context = getApplicationContext();
//		mGestureDetector = createGestureDetector(this);
		
		steps = (ArrayList<String[]>) this.getIntent().getSerializableExtra("steps");
		
		for (int i = 0; i < steps.size(); i++) {
			Log.v("test", Arrays.toString(steps.get(i)));
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
		for (int i = 0; i < steps.size(); i++) {
			menuItem = steps.get(i);
			menu.add(0, i, Menu.NONE, menuItem[1]);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
        // Handle item selection.
        switch (item.getItemId()) {
        	case 0:
        		return true;
        	case 1:
        		return true;
            case 2:
                return true;
        	case 3:
        		return true;
            case 4:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
		

	
}
