/************************************************************************
 *  Programmer Group: 
 *         Chad brucato
 *         Geoff Miller
 *         Noah Thompson
 *         
 *  Name:  Quiz App
 *  
 *  Due:   10-18-2013
 *  
 *  Notes:
 *  This file contains the implementation of the ResultsActivity Class
 ************************************************************************/
package com.example.androidfinal;

import java.util.ArrayList;

import edu.niu.cs.cbrucato.quizapplication.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/************************************************************************
 * Class: ResultsActivity
 * 
 * Notes:
 * This class generates the results activity in the quizApplication
 * project. This class unpacks session stat information and then
 * generates GUI objects to display these stats to the user.
 ************************************************************************/
public class ResultsActivity extends Activity {

    // ArrayList<Button> btnList;
    ArrayList<ListElement> elemList;
    ArrayList<TextView> txtvList;

    ArrayList<int[]> thisHistory;
    ArrayList<String> thisHistoryStamps;
    ArrayList<String> thisHistoryQType;
    Drawable elemBorder1;
    Drawable elemBorder2;

    long percent;

    /**************************************************
     * Override onOptionsItemSelected
     * 
     * Notes:
     * Override's the ActionBar's click action to
     * perform the "up" action. This will take you to
     * the hierarchical parent of the current activity:
     * MainActivity.
     **************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**************************************************
     * (Override the system's back button)
     * Override onKeyDown
     * 
     * Notes:
     * This overrides the system's back button but
     * instead of performing the back operation, which
     * takes you to the screen you were previously at,
     * it will instead perform the up operation. The up
     * operation works by taking you to the hierarchical 
     * parent of the activity. In this case, it will
     * take you to MainActivity instead of the
     * QuizzingActivity.
     **************************************************/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**************************************************
     * Override onCreate
     * 
     * Notes:
     * 
     * Overrides the onCreate method. This method
     * performs when the activity is created.
     **************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // setup Action Bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        thisHistory = MainActivity.getHistoryList();
        thisHistoryStamps = MainActivity.getHistoryStampsList();
        thisHistoryQType = MainActivity.getHistoryQTypeList();

        ScrollView MainView;
        LinearLayout ViewLayout;

        elemList = new ArrayList<ListElement>();
        txtvList = new ArrayList<TextView>();

        MainView = new ScrollView(this);
        elemBorder1 = this.getResources().getDrawable(R.drawable.custom_border);
        elemBorder2 = this.getResources().getDrawable(R.drawable.custom_border);

        ViewLayout = new LinearLayout(this);
        ViewLayout.setOrientation(LinearLayout.VERTICAL);
        ViewLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));

        // for each entry in the history
        for (int i = 0; i < thisHistory.size(); i++) {
            // add the TextViews
            txtvList.add(new TextView(this));
            txtvList.add(new TextView(this));
            txtvList.add(new TextView(this));

            // Define the text of the last 3 TextViews
            txtvList.get(txtvList.size() - 1).setText(
                    Integer.toString(thisHistory.get(i)[1]));
            txtvList.get(txtvList.size() - 2).setText(
                    Integer.toString(thisHistory.get(i)[0]) + " out of ");
            txtvList.get(txtvList.size() - 3).setText(
                    thisHistoryQType.get(i) + thisHistoryStamps.get(i) + ": ");

            // add elements toggling background color(by modulus)
            elemList.add(new ListElement(this, (i % 2)));
            elemList.get(elemList.size() - 1).addView(
                    txtvList.get(txtvList.size() - 3));
            elemList.get(elemList.size() - 1).addView(
                    txtvList.get(txtvList.size() - 2));
            elemList.get(elemList.size() - 1).addView(
                    txtvList.get(txtvList.size() - 1));
            ViewLayout.addView(elemList.get(elemList.size() - 1));
        }

        ViewLayout.requestLayout();
        MainView.addView(ViewLayout);
        setContentView(MainView);
    }

    /**********************************************************************
     * Class: ListElement
     * 
     * Notes:
     * This class inherits from LinearLayout. This class is used to create
     * GUI objects that can be added to the current activity and will
     * contain more GUI elements to generate the history list.
     **********************************************************************/
    class ListElement extends LinearLayout {
        public ListElement(Context context, int TypeName) {
            super(context);

            // set properties
            if (TypeName == 1) {
                this.setBackgroundColor(Color.LTGRAY);
                elemBorder1.setColorFilter(Color.LTGRAY,
                        PorterDuff.Mode.SRC_ATOP);
                this.setBackgroundDrawable(elemBorder1);
            }
            if (TypeName == 0) {
                this.setBackgroundColor(Color.GRAY);
                elemBorder2
                        .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                this.setBackgroundDrawable(elemBorder2);
            }

            this.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, 100));
        }
    }

    /**************************************************
     * Override onCreateOptionsMenu(Menu menu)
     * 
     * Notes:
     * Overrides the creation of the system menu.
     **************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }

}
