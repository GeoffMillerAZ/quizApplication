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
 *  This file contains the implementation of the QuizzingActivity class
 ************************************************************************/
package com.example.androidfinal;

import java.util.ArrayList;

import edu.niu.cs.cbrucato.quizapplication.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/************************************************************************
 * Class: QuizzingActivity
 * 
 * Notes:
 * This class pulls data from the MainActivity that include the quiz type
 * and number of questions selected by the user. The quizzing activity
 * then executes the desired quiz and then stores the final quiz stats
 * for the results activity to display.
 ************************************************************************/
public class QuizzingActivity extends Activity {

    // Declare interactive GUI objects
    TextView txtvQuestion, txtvTotQNum, txtvCurQNum, txtvQCorrect, txtvQWrong;
    Button btnTrue, btnFalse, btnNext, btnA, btnB, btnC, btnD;
    ProgressBar prgbrCurrQStatus;

    // declare utility variables
    int indexTF = -1; // index for TF questions
    int maxTF = 0; // max index for TF questions
    int indexMC = -1; // index for MC questions
    int maxMC = 0; // max index for MC questions
    int numQs = 0; // Number of questions this round
    int curQ = 0; // Current question number
    int qRight = 0; // Count of right answers this round
    int qWrong = 0; // Count of wrong answers this round
    int rnd = 0; // used to store a random value
    int quizChoice = 0; // The quiz type selected
    ArrayList<TrueFalse> questionBankTF = new ArrayList<TrueFalse>();
    ArrayList<MultChoice> questionBankMC = new ArrayList<MultChoice>();

    // action bar setup
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
     * Override the onCreate method for this activity.
     * This will cause the code within to execute upon
     * creation of CppActivity.
     **************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzing);

        // setup Action Bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve intent data
        Intent intent = getIntent();
        numQs = intent.getIntExtra("NUM_QUESTIONS", 0);
        quizChoice = intent.getIntExtra("QUIZ_CHOICE", 0);
        // Toast.makeText(this, quizChoice, Toast.LENGTH_SHORT).show();

        // Assign interactive GUI objects to xml ID's
        assignGUI();

        // Set question progress info
        upDateQProgress();

        // Populate questions
        populateQuestions(quizChoice);

        // get the first question
        updateQuestion();

        // set onClickListeners
        /**************************************************
         * Event Handler - btnTrue onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                onAnswerDo();
            }
        });

        /**************************************************
         * Event Handler - btnFalse onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                onAnswerDo();
            }
        });

        /**************************************************
         * Event Handler - btnNext onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ResultsActivity.class);

                if (curQ == numQs) {
                    finishSession(i);
                } else {
                    updateQuestion();
                }
            }
        });

        /**************************************************
         * Event Handler - btnA onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0);
                onAnswerDo();
            }
        });

        /**************************************************
         * Event Handler - btnB onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
                onAnswerDo();
            }
        });

        /**************************************************
         * Event Handler - btnC onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
                onAnswerDo();
            }
        });

        /**************************************************
         * Event Handler - btnD onClick
         * 
         * Notes:
         * Event to fire on the click of this button
         **************************************************/
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
                onAnswerDo();
            }
        });
    }

    /**************************************************
     * updateQuestion()
     * 
     * Notes:
     * Event to fire on the click of this button
     **************************************************/
    private void updateQuestion() {
        // disable the next button b/c the question isn't answered
        btnNext.setEnabled(false);

        // Get the next question randomly between TF or MC
        rnd = (int) (Math.random() * 2 + 1);
        
        // TF question
        if (rnd == 1) {
            indexTF = (indexTF + 1) % questionBankTF.size();
            int q = questionBankTF.get(indexTF).getQuestion();
            txtvQuestion.setText(q);
            prepGUIforTF();
        }
        // MC question
        else if (rnd == 2) {
            indexMC = (indexMC + 1) % questionBankMC.size();
            int q = questionBankMC.get(indexMC).getQuestion();
            txtvQuestion.setText(q);
            prepGUIforMC();
        }

        // Update question progress information
        txtvCurQNum.setText(Integer.toString(++curQ));
        prgbrCurrQStatus.setProgress(curQ);

        // enable the answer buttons so the user can submit an answer
        enableAnswerButtons();
    }

    /**************************************************
     * prepGUIforTF()
     * 
     * Notes:
     * Hide the MC-specific controls.
     * Un-hide the TF-specific controls.
     **************************************************/
    private void prepGUIforTF() {
        btnA.setVisibility(View.GONE);
        btnB.setVisibility(View.GONE);
        btnC.setVisibility(View.GONE);
        btnD.setVisibility(View.GONE);

        btnTrue.setVisibility(View.VISIBLE);
        btnFalse.setVisibility(View.VISIBLE);
    }
    
    /**************************************************
     * prepGUIforMC()
     * 
     * Notes:
     * Un-Hide the MC-specific controls.
     * Hide the TF-specific controls. 
     **************************************************/
    private void prepGUIforMC() {
        btnTrue.setVisibility(View.GONE);
        btnFalse.setVisibility(View.GONE);

        btnA.setVisibility(View.VISIBLE);
        btnB.setVisibility(View.VISIBLE);
        btnC.setVisibility(View.VISIBLE);
        btnD.setVisibility(View.VISIBLE);
    }
    
    /**************************************************
     * disableAnswerButtions()
     * 
     * Notes:
     * Disable all buttons used to answer questions.
     * This is used to control user's input. By doing
     * this it prevents the user from entering an answer
     * and then changing their answer after seeing the
     * result. These buttons should be enabled after
     * hitting the next button.
     **************************************************/
    private void disableAnswerButtions() {
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        btnTrue.setEnabled(false);
        btnFalse.setEnabled(false);
    }

    /**************************************************
     * enableAnswerButtons()
     * 
     * Notes:
     * Enables the answer buttons so that the user can
     * answer a question.
     **************************************************/
    private void enableAnswerButtons() {
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);
        btnTrue.setEnabled(true);
        btnFalse.setEnabled(true);
    }

    /**************************************************
     * onAnswerDo()
     * 
     * Notes:
     * Enable the next button so that the user can
     * proceed to the next question. Also disable
     * answer buttons.
     **************************************************/
    private void onAnswerDo() {
        btnNext.setEnabled(true);
        disableAnswerButtions();
    }

    /**************************************************
     * checkAnswer(boolean b)
     * Arguments:
     * ..... boolean b
     * ............ A bool that represents the user's
     * ............ answer.
     * 
     * Notes:
     * Compares the user's answer against the correct
     * answer. Returns the logical comparison between
     * the two.
     **************************************************/
    private void checkAnswer(boolean b) {
        // This gets the correct answer from the question bank
        boolean answerIsTrue = questionBankTF.get(indexTF).isTrueQuestion();
        
        // if the user was right
        if (b == answerIsTrue) {
            answeredRight();
        }
        // if the user was wrong
        else {
            answeredWrong();
        }
    }

    /**************************************************
     * checkAnswer(int i)
     * Arguments:
     * ..... int i
     * ............ An int representing the index value
     * ............ of the user's choice
     * 
     * Notes:
     * Compares the user's answer against the correct
     * answer. Returns the logical comparison between
     * the two.
     **************************************************/
    private void checkAnswer(int i) {
        if (questionBankMC.get(indexMC).isMultChoice(i)) {
            answeredRight();
        } else {
            answeredWrong();
        }
    }

    /**************************************************
     * answeredRight()
     * 
     * Notes:
     * Toast Correct and increment GUI
     **************************************************/
    private void answeredRight() {
        Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        txtvQCorrect.setText(Integer.toString(++qRight));
    }

    /**************************************************
     * answeredWrong()
     * 
     * Notes:
     * Toast Wrong and increment GUI
     **************************************************/
    private void answeredWrong() {
        Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        txtvQWrong.setText(Integer.toString(++qWrong));
    }

    /**************************************************
     * Override: onCreateOptionsMenu
     * 
     * Notes:
     * Creating the system menu
     **************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**************************************************
     * upDateQProgress()
     * 
     * Notes:
     * Setup GUI objects related to quiz progress
     **************************************************/
    private void upDateQProgress() {
        txtvTotQNum.setText(Integer.toString(numQs));
        txtvCurQNum.setText(Integer.toString(curQ));
        prgbrCurrQStatus.setMax(numQs);
    }

    /**************************************************
     * finishSession()
     * Arguments:
     * ..... Intent i
     * ............ An Intent that represents the Intent
     * ............ to be launched.
     * 
     * Notes:
     * Store relevant data for use by other activities
     * and launch the next activity.
     **************************************************/
    private void finishSession(Intent i) {
        String qChoiceString;

        // disable the next button so the user can't do this twice
        btnNext.setEnabled(false);
        i.putExtra("Q_RIGHT", qRight);
        i.putExtra("NUM_QUESTIONS", numQs);
        i.putExtra("QUIZ_CHOICE", quizChoice);
        startActivity(i);
        // Data communication between activities
        MainActivity.addToHistoryList(qRight, numQs);
        MainActivity.addToHistoryStampsList();
        if (quizChoice == 1)
            qChoiceString = "C++  | ";
        else if (quizChoice == 2)
            qChoiceString = "A    | ";
        else if (quizChoice == 3)
            qChoiceString = "Java | ";
        else
            qChoiceString = "?    | ";
        MainActivity.addToHistoryQTypeList(qChoiceString);
    }

    /**************************************************
     * assignGUI()
     * 
     * Notes:
     * Assign important GUI objects to the xmbl IDs
     * for each respective object.
     **************************************************/
    private void assignGUI() {
        txtvQuestion = (TextView) findViewById(R.id.txtvQuestion);
        txtvTotQNum = (TextView) findViewById(R.id.txtvTotQNum);
        txtvCurQNum = (TextView) findViewById(R.id.txtvCurQNum);
        txtvQCorrect = (TextView) findViewById(R.id.txtvQCorrect);
        txtvQWrong = (TextView) findViewById(R.id.txtvQWrong);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnNext = (Button) findViewById(R.id.btnNextQ);
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);
        prgbrCurrQStatus = (ProgressBar) findViewById(R.id.prgbrCurrQStatus);
    }

    /**************************************************
     * populateQuestions(int quizType)
     * 
     * Arguments:
     * ..... int quizType
     * ............ An int representing the type of quiz
     * ............ to be run.
     * 
     * Notes:
     * Launches the appropriate function to populate the
     * question banks with the appropriate question types.
     **************************************************/
    private void populateQuestions(int quizType) {
        // type 1 is c++
        if (quizType == 1) {
            populateQuestionsCpp();
        }
        // type 2 is assembly
        else if (quizType == 2) {
            populateQuestionsAssembly();
        }
        // type 3 is Java
        else if (quizType == 3) {
            populateQuestionsJava();
        }
        // choose c++ by default (on error)
        else {
            populateQuestionsCpp();
        }

    }

    /**************************************************
     * populateQuestionsCpp()
     * 
     * Notes:
     * Populates question banks with c++ questions
     **************************************************/
    private void populateQuestionsCpp() {
        // Question bank array for TrueFalse questions
        questionBankTF.add(new TrueFalse(R.string.CqTF1, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF2, true));
        questionBankTF.add(new TrueFalse(R.string.CqTF3, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF4, true));
        questionBankTF.add(new TrueFalse(R.string.CqTF5, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF6, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF7, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF8, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF9, false));
        questionBankTF.add(new TrueFalse(R.string.CqTF10, true));
        maxTF = questionBankTF.size() - 1;
        indexTF = (int) (Math.random() * maxTF);

        // Question bank array for TrueFalse questions
        questionBankMC.add(new MultChoice(R.string.CqMC1, 1));
        questionBankMC.add(new MultChoice(R.string.CqMC2, 2));
        questionBankMC.add(new MultChoice(R.string.CqMC3, 1));
        questionBankMC.add(new MultChoice(R.string.CqMC4, 3));
        questionBankMC.add(new MultChoice(R.string.CqMC5, 0));
        questionBankMC.add(new MultChoice(R.string.CqMC6, 2));
        questionBankMC.add(new MultChoice(R.string.CqMC7, 2));
        questionBankMC.add(new MultChoice(R.string.CqMC8, 0));
        questionBankMC.add(new MultChoice(R.string.CqMC9, 3));
        questionBankMC.add(new MultChoice(R.string.CqMC10, 1));
        maxMC = questionBankTF.size() - 1;
        indexMC = (int) (Math.random() * maxMC);
    }

    /**************************************************
     * populateQuestionsAssembly()
     * 
     * Notes:
     * Populates question banks with assembly questions
     **************************************************/
    private void populateQuestionsAssembly() {
        // Question bank array for TrueFalse questions
        questionBankTF.add(new TrueFalse(R.string.AqTF1, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF2, true));
        questionBankTF.add(new TrueFalse(R.string.AqTF3, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF4, true));
        questionBankTF.add(new TrueFalse(R.string.AqTF5, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF6, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF7, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF8, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF9, false));
        questionBankTF.add(new TrueFalse(R.string.AqTF10, true));
        maxTF = questionBankTF.size() - 1;
        indexTF = (int) (Math.random() * maxTF);

        // Question bank array for TrueFalse questions
        questionBankMC.add(new MultChoice(R.string.AqMC1, 1));
        questionBankMC.add(new MultChoice(R.string.AqMC2, 2));
        questionBankMC.add(new MultChoice(R.string.AqMC3, 1));
        questionBankMC.add(new MultChoice(R.string.AqMC4, 3));
        questionBankMC.add(new MultChoice(R.string.AqMC5, 0));
        questionBankMC.add(new MultChoice(R.string.AqMC6, 2));
        questionBankMC.add(new MultChoice(R.string.AqMC7, 2));
        questionBankMC.add(new MultChoice(R.string.AqMC8, 0));
        questionBankMC.add(new MultChoice(R.string.AqMC9, 3));
        questionBankMC.add(new MultChoice(R.string.AqMC10, 1));
        maxMC = questionBankTF.size() - 1;
        indexMC = (int) (Math.random() * maxMC);
    }

    /**************************************************
     * populateQuestionsJava()
     * 
     * Notes:
     * Populates question banks with java questions
     **************************************************/
    private void populateQuestionsJava() {
        // Question bank array for TrueFalse questions
        questionBankTF.add(new TrueFalse(R.string.JqTF1, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF2, true));
        questionBankTF.add(new TrueFalse(R.string.JqTF3, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF4, true));
        questionBankTF.add(new TrueFalse(R.string.JqTF5, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF6, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF7, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF8, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF9, false));
        questionBankTF.add(new TrueFalse(R.string.JqTF10, true));
        maxTF = questionBankTF.size() - 1;
        indexTF = (int) (Math.random() * maxTF);

        // Question bank array for TrueFalse questions
        questionBankMC.add(new MultChoice(R.string.JqMC1, 1));
        questionBankMC.add(new MultChoice(R.string.JqMC2, 2));
        questionBankMC.add(new MultChoice(R.string.JqMC3, 1));
        questionBankMC.add(new MultChoice(R.string.JqMC4, 3));
        questionBankMC.add(new MultChoice(R.string.JqMC5, 0));
        questionBankMC.add(new MultChoice(R.string.JqMC6, 2));
        questionBankMC.add(new MultChoice(R.string.JqMC7, 2));
        questionBankMC.add(new MultChoice(R.string.JqMC8, 0));
        questionBankMC.add(new MultChoice(R.string.JqMC9, 3));
        questionBankMC.add(new MultChoice(R.string.JqMC10, 1));
        maxMC = questionBankTF.size() - 1;
        indexMC = (int) (Math.random() * maxMC);
    }
}