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
 *  This app provides quizzes in either c++, assembly or java programming.
 *  The user selects the number of questions and the desired quiz type.
 *  The app then launches a quizzing activity that randomly picks between
 *  banks of different question types including True and False and multiple
 *  choice questions. Upon completing the desired number of questions, the
 *  app stores the results and passes to a results activity. The results
 *  activity displays this sessions history of quizzing activity.
 ************************************************************************/
package com.example.androidfinal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.niu.cs.cbrucato.quizapplication.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/************************************************************************
*  Main Class: MainActivity
*  This class provides the interface and tools to setup the desired
*  quizzing activity.
************************************************************************/
public class MainActivity extends Activity {

    /**************************************************
     * Declaration of important GUI objects on this
     * activity.
     **************************************************/
    Button btnCpp, btnJava, btnAssembly, btnHistory;
    SeekBar skbrNumQs;
    TextView txtvNumQuest;
    
    // Global Variables
    static ArrayList<int[]> historyList = new ArrayList<int[]>();
    static ArrayList<String> historyStampsList = new ArrayList<String>();
    static ArrayList<String> historyQTypeList = new ArrayList<String>();

    /**************************************************
     * Override the onCreate method for this activity.
     * This will cause the code within to execute upon
     * creation of MainActivity.
     **************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Declare and assign to id(xml) the text view for number of questions
        txtvNumQuest = (TextView) findViewById(R.id.txtvNumQuest);
        // Declare button and assign to btnCpp from the xml
        btnCpp = (Button) findViewById(R.id.btnCpp);
        /**************************************************
         * Event Handler - btnCpp onClick
         * 
         * Notes:
         * This launches the c++ activity
         **************************************************/
        btnCpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass quiz preferences to the quizzing activity
                Intent i = new Intent(v.getContext(), QuizzingActivity.class);
                i.putExtra("NUM_QUESTIONS", Integer.parseInt(txtvNumQuest.getText().toString()));
                i.putExtra("QUIZ_CHOICE", 1);
                startActivity(i);
            }
        });

        // Declare button and assign to btnJava from the xml
        btnJava = (Button) findViewById(R.id.btnJava);
        /**************************************************
         * Event Handler - btnJava onClick
         * 
         * Notes:
         * This launches the java quiz.
         **************************************************/
        btnJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), QuizzingActivity.class);
                // Pass quiz preferences to the quizzing activity
                i.putExtra("NUM_QUESTIONS", Integer.parseInt(txtvNumQuest.getText().toString()));
                i.putExtra("QUIZ_CHOICE", 3);
                startActivity(i);
            }
        });

        // Declare button and assign to btnAssembly from the xml
        btnAssembly = (Button) findViewById(R.id.btnAssembly);
        /*************************************************
         * Event Handler - btnAssembly onClick
         * 
         * Notes:
         * This launches the assembler quiz
         **************************************************/
        btnAssembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), QuizzingActivity.class);
                // Pass quiz preferences to the quizzing activity
                i.putExtra("NUM_QUESTIONS", Integer.parseInt(txtvNumQuest.getText().toString()));
                i.putExtra("QUIZ_CHOICE", 2);
                startActivity(i);
            }
        });
        
     // Declare button and assign to btnHistory from the xml
        btnHistory = (Button) findViewById(R.id.btnHistory);
        /*************************************************
         * Event Handler - btnHistory onClick
         * 
         * Notes:
         * This allows the user to launch to the results activity
         **************************************************/
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ResultsActivity.class);
                startActivity(i);
            }
        });

        // Declare the SeekBar and assign to skbrNumQuestions from xml
        skbrNumQs = (SeekBar) findViewById(R.id.skbrNumQuestions);
        // Event Handlers
        skbrNumQs.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            /**************************************************
             * Event Handler - skbrNumQs onStopTrackingTouch
             * 
             * Notes:
             * This is what occurs at the stop of changing the
             * seek bar's progress.
             **************************************************/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            /**************************************************
             * Event Handler - skbrNumQs onStartTrackingTouch
             * 
             * Notes:
             * This is what occurs at the start of changing the
             * seek bar's progress.
             **************************************************/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**************************************************
             * Event Handler - skbrNumQs onProgressChanged
             * 
             * Notes:
             * This matches the seek bar's progress to the textview
             * that displays the selected number of questions.
             **************************************************/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                txtvNumQuest.setText(Integer.toString(skbrNumQs.getProgress()));
            }
        });
    }
    
    /**************************************************
     * static ArrayList<String> getHistoryStampsList()
     * 
     * Notes:
     * This retrieves the static historyStampsList object
     **************************************************/
    public static ArrayList<String> getHistoryStampsList() {
        return historyStampsList;
    }

    /**************************************************
     * static void addToHistoryStampsList()
     * 
     * Notes:
     * This adds the current time to the static historyStampsList
     **************************************************/
    public static void addToHistoryStampsList() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                " E MMM dd HH:mm:ss");
        String timeStamp = formatter.format(currentDate.getTime());
        
        historyStampsList.add(timeStamp);
    }
    
    /**************************************************
     * public static ArrayList<int[]> getHistoryList()
     * 
     * Notes:
     * This retrieves the static historyList
     **************************************************/
    public static ArrayList<int[]> getHistoryList() {
        return historyList;
    }

    /**************************************************
     * static void addToHistoryList(int numRightIn, int numQsIn)
     * 
     * Arguments:
     * ..... int numRightIn
     * ............ An int representing the number of
     * ............ questions answered correctly
     * ..... int numQsIn
     * ............ An int representing the number of
     * ............ questions total
     * 
     * Notes:
     * This adds an array of ints to the static historyList
     * that represent the user's score for this game.
     **************************************************/
    public static void addToHistoryList(int numRightIn, int numQsIn) {
        historyList.add(new int[] {numRightIn, numQsIn});
    }
    
    /**************************************************
     * static ArrayList<String> getHistoryQTypeList()
     * 
     * Notes:
     * 
     **************************************************/
    public static ArrayList<String> getHistoryQTypeList() {
        return historyQTypeList;
    }

    /**************************************************
     * static void addToHistoryQTypeList(String qTypeString)
     * 
     * Arguments:
     * ..... String qTypeString
     * ............ A String representing the quiz type
     * 
     * Notes:
     * This adds the qTypeString to the historyQTypeList
     **************************************************/
    public static void addToHistoryQTypeList(String qTypeString) {
        historyQTypeList.add(qTypeString);
    }
}