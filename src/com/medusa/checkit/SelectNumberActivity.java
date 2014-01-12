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

	TextView enteredNumber;
	String numberAsString;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_number);
		enteredNumber = (TextView)findViewById(R.id.entered_number);
		numberAsString = "";
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
        		numberAsString += "1";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_two:
        		numberAsString += "2";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_three:
        		numberAsString += "3";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_four:
        		numberAsString += "4";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_five:
        		numberAsString += "5";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_six:
        		numberAsString += "6";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_seven:
        		numberAsString += "7";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_eight:
        		numberAsString += "8";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_nine:
        		numberAsString += "9";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_zero:
        		numberAsString += "0";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_dot:
        		numberAsString += ".";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.value_negative:
        		numberAsString += "-";
        		enteredNumber.setText(numberAsString);
        		return true;
        	case R.id.finish_number:
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
	
}
