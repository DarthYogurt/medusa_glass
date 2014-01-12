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

	TextView enteredNumber;
	String number;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_number);
		enteredNumber = (TextView)findViewById(R.id.entered_number);
		number = "";
	}
	
	@Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_number, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        switch (item.getItemId()) {
        	case R.id.value_one:
        		number += "1";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_two:
        		number += "2";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_three:
        		number += "3";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_four:
        		number += "4";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_five:
        		number += "5";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_six:
        		number += "6";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_seven:
        		number += "7";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_eight:
        		number += "8";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_nine:
        		number += "9";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_zero:
        		number += "0";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_dot:
        		number += ".";
        		enteredNumber.setText(number);
        		openOptionsMenu();
        		return true;
        	case R.id.value_negative:
        		number += "-";
        		enteredNumber.setText(number);
        		openOptionsMenu();
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
	
}
