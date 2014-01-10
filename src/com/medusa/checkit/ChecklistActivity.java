package com.medusa.checkit;

import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ChecklistActivity extends Activity {
	
	private List<Card> mCards;
    private CardScrollView mCardScrollView;
	private ArrayList<String[]> steps;
	private String[] currentStep;
	private String stepType;
	private boolean resultYesNo;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		steps = (ArrayList<String[]>) this.getIntent().getSerializableExtra("steps");
		
		createCards();
        mCardScrollView = new CardScrollView(this);
        StepCardScrollAdapter adapter = new StepCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
        
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		currentStep = steps.get(position);
        		stepType = currentStep[2];
    			openOptionsMenu();
    		}
        });
	}
	
	private void createCards() {
        mCards = new ArrayList<Card>();
        Card card;
        String[] step;
        
        for (int i = 0; i < steps.size(); i++) {
        	step = steps.get(i);
        	card = new Card(this);
        	card.setText(step[1]);
        	card.setFootnote("Step #" + step[0]);
        	mCards.add(card);
        }
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
		
		if (stepType.equalsIgnoreCase("bool")) {
			menu.add(Menu.NONE, 1, Menu.NONE, "Yes");
			menu.add(Menu.NONE, 2, Menu.NONE, "No");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		if (stepType.equalsIgnoreCase("double")){
			menu.add(Menu.NONE, 3, Menu.NONE, "Enter Number");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		if (stepType.equalsIgnoreCase("text")){
			menu.add(Menu.NONE, 4, Menu.NONE, "Record Message");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		if (stepType.equalsIgnoreCase("file")){
			menu.add(Menu.NONE, 5, Menu.NONE, "Take Picture");
			menu.add(Menu.NONE, 6, Menu.NONE, "Record Video");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
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
				resultYesNo = true;
				Log.v("selected", "yes");
				return true;
			case 2:
				resultYesNo = false;
				Log.v("selected", "no");
				return true;
			case 3:
				Log.v("selected", "enter number");
				return true;
			case 4:
				Log.v("selected", "record message");
				return true;
			case 5:
				Log.v("selected", "take picture");
				takePicture();
				return true;
			case 6:
				Log.v("selected", "record video");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}
	
}
