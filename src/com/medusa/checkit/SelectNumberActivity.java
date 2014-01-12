package com.medusa.checkit;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SelectNumberActivity extends Activity {
	
	private static final int NUMBER_REQUEST = 5;

	TextView number;
	String numberAsString;
	boolean isNegative;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_number);
		number = (TextView)findViewById(R.id.entered_number);
		numberAsString = "";
	}
	
	@Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }
	
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.select_number, menu);
//        return true;
//    }
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(Menu.NONE, 1, Menu.NONE, "1");
		menu.add(Menu.NONE, 2, Menu.NONE, "2");
		menu.add(Menu.NONE, 3, Menu.NONE, "3");
		menu.add(Menu.NONE, 4, Menu.NONE, "4");
		menu.add(Menu.NONE, 5, Menu.NONE, "5");
		menu.add(Menu.NONE, 6, Menu.NONE, "6");
		menu.add(Menu.NONE, 7, Menu.NONE, "7");
		menu.add(Menu.NONE, 8, Menu.NONE, "8");
		menu.add(Menu.NONE, 9, Menu.NONE, "9");
		menu.add(Menu.NONE, 0, Menu.NONE, "0");
		menu.add(Menu.NONE, 10, Menu.NONE, ".");
        if (!isNegative) { menu.add(Menu.NONE, 11, Menu.NONE, "Make Negative"); } 
        if (isNegative) { menu.add(Menu.NONE, 12, Menu.NONE, "Make Positive"); }
        menu.add(Menu.NONE, 13, Menu.NONE, "Finish Number");
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
        	case 1:
        		numberAsString += "1";
        		number.setText(numberAsString);
        		return true;
        	case 2:
        		numberAsString += "2";
        		number.setText(numberAsString);
        		return true;
        	case 3:
        		numberAsString += "3";
        		number.setText(numberAsString);
        		return true;
        	case 4:
        		numberAsString += "4";
        		number.setText(numberAsString);
        		return true;
        	case 5:
        		numberAsString += "5";
        		number.setText(numberAsString);
        		return true;
        	case 6:
        		numberAsString += "6";
        		number.setText(numberAsString);
        		return true;
        	case 7:
        		numberAsString += "7";
        		number.setText(numberAsString);
        		return true;
        	case 8:
        		numberAsString += "8";
        		number.setText(numberAsString);
        		return true;
        	case 9:
        		numberAsString += "9";
        		number.setText(numberAsString);
        		return true;
        	case 0:
        		numberAsString += "0";
        		number.setText(numberAsString);
        		return true;
        	case 10:
        		numberAsString += ".";
        		number.setText(numberAsString);
        		return true;
        	case 11:
        		makeNumberNegative();
        		number.setText(numberAsString);
        		return true;
        	case 12:
        		makeNumberPositive();
        		number.setText(numberAsString);
        		return true;
        	case 13:
        		Intent intent = new Intent();
        		intent.putExtra("numberAsString", numberAsString);
        		setResult(NUMBER_REQUEST, intent);
        		finish();
        		return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
        	openOptionsMenu();
//        	mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
            return true;
        }
        return false;
    }
	
	private void makeNumberNegative() {
		numberAsString = "-" + numberAsString;
		isNegative = true;
	}
	
	private void makeNumberPositive() {
		numberAsString = numberAsString.substring(1);
		isNegative = false;
	}
	
}
