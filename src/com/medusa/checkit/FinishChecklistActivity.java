package com.medusa.checkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;

public class FinishChecklistActivity extends Activity {
	
	private AudioManager mAudioManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_finish_checklist);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	}
	
	@Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
        	startActivity(new Intent(this, SplashActivity.class));
        	mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
            return true;
        }
        return false;
    }
}
