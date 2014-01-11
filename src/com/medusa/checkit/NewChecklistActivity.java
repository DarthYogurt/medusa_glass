package com.medusa.checkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class NewChecklistActivity extends Activity {
	
	private AudioManager mAudioManager;
	private GestureDetector mGestureDetector;
	private List<Card> mCards;
    private CardScrollView mCardScrollView;
    StepCardScrollAdapter adapter;
    
	Intent checklistIntent;
	String allStepsJSONString;
	ArrayList<String[]> allStepsArray;
	ArrayList<String[]> checklistsArray;
	ArrayList<String[]> stepsArray;
	ArrayList<String[]> checklistSteps;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//		mGestureDetector = createGestureDetector(this);
		
		checklistsArray = (ArrayList<String[]>) this.getIntent().getSerializableExtra("checklists");
		stepsArray = (ArrayList<String[]>) this.getIntent().getSerializableExtra("steps");
		
		createCards();
        mCardScrollView = new CardScrollView(this);
        adapter = new StepCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
        
        checklistIntent = new Intent(this, ChecklistActivity.class);
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			checklistIntent.putExtra("steps", getChecklistSteps(position));
    			startActivity(checklistIntent);
    			mAudioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
    		}
        });
        
	}
	
	private void createCards() {
        mCards = new ArrayList<Card>();
        Card card;
        String[] checklist;
        
        for (int i = 0; i < checklistsArray.size(); i++) {
        	checklist = checklistsArray.get(i);
        	card = new Card(this);
        	card.setText(checklist[1]);
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
	
	private ArrayList<String[]> getChecklistSteps(int id) {
		checklistSteps = new ArrayList<String[]>();
		String[] array = checklistsArray.get(id);
		String checklistId = array[0];
		
		for (int i = 0; i < stepsArray.size(); i++) {
			String[] step = stepsArray.get(i);
			if (step[5].equals(checklistId)) {
				checklistSteps.add(step);
			}
		}
		return checklistSteps;
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
//		for (int i = 0; i < checklistsArray.size(); i++) {
//			menuItem = checklistsArray.get(i);
//			menu.add(0, i, Menu.NONE, menuItem[1]);
//		}
//		return super.onPrepareOptionsMenu(menu);
//	}
//	
//	@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//		checklistIntent = new Intent(this, ChecklistActivity.class);
//		checklistIntent.putExtra("steps", getChecklistSteps(item.getItemId()));
//		startActivity(checklistIntent);
//		return true;
//		
////        // Handle item selection.
////        switch (item.getItemId()) {
////        	case 0:
////        		checklistIntent.putExtra("steps", getChecklistSteps(0));
////        		startActivity(checklistIntent);
////        		return true;
////        	case 1:
////        		checklistIntent.putExtra("steps", getChecklistSteps(1));
////        		startActivity(checklistIntent);
////        		return true;
////            case 2:
////            	checklistIntent.putExtra("steps", getChecklistSteps(2));
////        		startActivity(checklistIntent);
////                return true;
////        	case 3:
////        		checklistIntent.putExtra("steps", getChecklistSteps(3));
////        		startActivity(checklistIntent);
////        		return true;
////            case 4:
////            	checklistIntent.putExtra("steps", getChecklistSteps(4));
////        		startActivity(checklistIntent);
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
//    }
	
//  === GESTURE DETECTION ===	
//	private GestureDetector createGestureDetector(Context context) {
//	    GestureDetector gestureDetector = new GestureDetector(context);
//	    
//        //Create a base listener for generic gestures
//        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
//            @Override
//            public boolean onGesture(Gesture gesture) {
//                if (gesture == Gesture.TAP) {
//                	// do something on one finger tap
//                    return true;
//                } else if (gesture == Gesture.TWO_TAP) {
//                    // do something on two finger tap
//                    return true;
//                } else if (gesture == Gesture.SWIPE_RIGHT) {
//                    // do something on right (forward) swipe
//                    return true;
//                } else if (gesture == Gesture.SWIPE_LEFT) {
//                    // do something on left (backwards) swipe
//                    return true;
//                }
//                return false;
//            }
//        });
//        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
//            @Override
//            public void onFingerCountChanged(int previousCount, int currentCount) {
//              // do something on finger count changes
//            }
//        });
//        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
//            @Override
//            public boolean onScroll(float displacement, float delta, float velocity) {
//                // do something on scrolling
//            	return true;
//            }
//        });
//        return gestureDetector;
//    }
//	
//    // Send generic motion events to the gesture detector
//    @Override
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        if (mGestureDetector != null) {
//            return mGestureDetector.onMotionEvent(event);
//        }
//        return false;
//    }

}
