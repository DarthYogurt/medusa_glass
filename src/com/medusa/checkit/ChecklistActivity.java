package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ChecklistActivity extends Activity {
	
	private AudioManager mAudioManager;
	private HTTPPostThread postThread;
	private List<Card> mCards;
	private StepCardScrollAdapter adapter;
    private CardScrollView mCardScrollView;
    private JSONWriter jsonWriter;
	private ArrayList<String[]> steps;
	private Card currentCard;
	private String[] currentStepArray;
	private int checklistId;
	private int currentStepId;
	private String currentStepType;
	

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		postThread = new HTTPPostThread();
		steps = (ArrayList<String[]>) this.getIntent().getSerializableExtra("steps");
		
		createCards();
        mCardScrollView = new CardScrollView(this);
        adapter = new StepCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
        
        // Creates new JSON file and initializes some values
        currentStepArray = steps.get(0);
        checklistId = Integer.parseInt(currentStepArray[5]);
        try {
        	jsonWriter = new JSONWriter(this);
			jsonWriter.startNewChecklist(checklistId);
		} catch (IOException e) { e.printStackTrace(); }
        
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		
        		if (position == mCards.size() - 1) {
        			try { jsonWriter.finishNewChecklist(); } 
    				catch (IOException e) { e.printStackTrace(); }
    				postThread.start();
    				Log.v("HTTP POST", "Checklist JSON sent to server");
    				startActivity(new Intent(getApplicationContext(), FinishChecklistActivity.class));
    				mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
        		}
        		else {
        			currentCard = mCards.get(position);
            		currentStepArray = steps.get(position);
            		currentStepId = Integer.parseInt(currentStepArray[3]);
            		currentStepType = currentStepArray[2];
        			openOptionsMenu();
        		}
    		}
        });
	}
	
	private void createCards() {
        mCards = new ArrayList<Card>();
        Card card;
        String[] step;
        
        // Dynamically set up Cards based on ArrayList of steps
        for (int i = 0; i < steps.size(); i++) {
        	step = steps.get(i);
        	card = new Card(this);
        	card.setText(step[1]);
//        	card.setFootnote("Step #" + step[0]);
        	card.setFootnote("Result:");
        	mCards.add(card);
        }
        
        // Makes Finish Checklist Card
        card = new Card(this);
        card.setText("Finish Checklist");
        mCards.add(card);
    }
	
	private class StepCardScrollAdapter extends CardScrollAdapter {

        @Override
        public int findIdPosition(Object id) {
            return -1;
        }

        @Override
        public int findItemPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).toView();
        }
    }	
	
	// Use this instead of onCreateOptionsMenu if menu is dynamic
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		if (currentStepType.equalsIgnoreCase("bool")) {
			menu.add(Menu.NONE, 1, Menu.NONE, "Yes");
			menu.add(Menu.NONE, 2, Menu.NONE, "No");
		}
		
		if (currentStepType.equalsIgnoreCase("double")){
			menu.add(Menu.NONE, 3, Menu.NONE, "Enter Number");
		}
		
		if (currentStepType.equalsIgnoreCase("text")){
			menu.add(Menu.NONE, 4, Menu.NONE, "Record Message");
		}
		
		if (currentStepType.equalsIgnoreCase("file")){
			menu.add(Menu.NONE, 5, Menu.NONE, "Take Picture");
			menu.add(Menu.NONE, 6, Menu.NONE, "Record Video");
		}
		
		menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*	itemId 1 = Yes/True
			itemId 2 = No/False
			itemId 3 = Enter Number
			itemId 4 = Record Message
			itemId 5 = Take Picture
			itemId 6 = Record Video
		*/
		switch (item.getItemId()) {
			case 1:
				currentCard.setFootnote("Result: YES");
				adapter.notifyDataSetChanged();
				try { jsonWriter.writeStepBoolean(currentStepId, true);	} 
				catch (IOException e) { e.printStackTrace(); }
				return true;
			case 2:
				currentCard.setFootnote("Result: NO");
				adapter.notifyDataSetChanged();
				try { jsonWriter.writeStepBoolean(currentStepId, false); } 
				catch (IOException e) { e.printStackTrace(); }
				return true;
			case 3:
				currentCard.setFootnote("Result: NUMBER ENTERED");
				adapter.notifyDataSetChanged();
				return true;
			case 4:
				recordMessage();
				currentCard.setFootnote("Result: MESSAGE RECORDED");
				adapter.notifyDataSetChanged();
				return true;
			case 5:
				takePicture();
				currentCard.setFootnote("Result: PICTURE TAKEN");
				adapter.notifyDataSetChanged();
				return true;
			case 6:
				takeVideo();
				currentCard.setFootnote("Result: VIDEO RECORDED");
				adapter.notifyDataSetChanged();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void recordMessage() {
	    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    startActivityForResult(intent, 0);
	}
	
	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}
	
	private void takeVideo() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		startActivityForResult(intent, 1);
	}
	
	private class HTTPPostThread extends Thread {
		
		@Override
		public void run() {
			HTTPPostRequest post = new HTTPPostRequest(getApplicationContext());
			try {
				post.multipartPost(JSONWriter.CHECKLIST_FILENAME);
			} catch (ClientProtocolException e) { e.printStackTrace(); } 
			catch (IOException e) { e.printStackTrace(); }
		}
	}

}