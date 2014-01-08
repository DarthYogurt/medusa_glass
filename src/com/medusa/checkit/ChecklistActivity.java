package com.medusa.checkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ChecklistActivity extends Activity {
	
	private List<Card> mCards;
    private CardScrollView mCardScrollView;
	private ArrayList<String[]> steps;

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
	
//	@Override
//    public void onResume() {
//        super.onResume();
//        openOptionsMenu();
//    }
//	
//	// Use this instead of onCreateOptionsMenu if menu is dynamic
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//		String[] menuItem;
//		
//		menu.clear();
//		for (int i = 0; i < steps.size(); i++) {
//			menuItem = steps.get(i);
//			menu.add(0, i, Menu.NONE, menuItem[1]);
//		}
//		return super.onPrepareOptionsMenu(menu);
//	}
//	
//	@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//		
//        // Handle item selection.
//        switch (item.getItemId()) {
//        	case 0:
//        		return true;
//        	case 1:
//        		return true;
//            case 2:
//                return true;
//        	case 3:
//        		return true;
//            case 4:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
	
}
