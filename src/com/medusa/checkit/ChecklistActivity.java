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
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
}
