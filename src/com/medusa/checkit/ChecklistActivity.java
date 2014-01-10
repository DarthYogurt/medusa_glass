package com.medusa.checkit;

import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Yes");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "No");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		if (stepType.equalsIgnoreCase("text")){
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Record Message");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		if (stepType.equalsIgnoreCase("double")){
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Enter Number");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		if (stepType.equalsIgnoreCase("file")){
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Take Picture");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Record Video");
			menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Cancel");
		}
		
		return super.onPrepareOptionsMenu(menu);
	}
	
}
