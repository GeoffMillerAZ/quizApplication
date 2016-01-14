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
 *  This file contains the implementation of the MultChoice Class
 ************************************************************************/
package com.example.androidfinal;


/************************************************************************
*  Class: MultChoice
*  
*  Notes:
*  This class contains members and functions necessary to create a
*  Multiple Choice question and then validate a user's answer vs. the
*  correct answer.
************************************************************************/
public class MultChoice {
    int question;
    //this is a resource id for the question
    int correctAnswerIndex;    
    
    /*********************************************
     * CONSTRUCTOR
     * 
     * Arguments:
     * ..... int q:
     * ....... An Integer representing the resource
     * ....... index.
     *  
     * ......int mCIn:
     * ....... An int representing the correct answer
     * ....... index to the question being constructed
     * 
     * Notes:
     * This constructor creates an instance of MultChoice
     * question and sets the resource index and correct
     * answer index.
     *********************************************/
    public MultChoice( int q, int mCIn){
        question = q;
        correctAnswerIndex = mCIn;
    }
    
    /*********************************************
     * getQuestion()
     * 
     * Notes:
     * Gets the resource index for the question
     *********************************************/
    public int getQuestion(){
        return question;
    }
    
    /*********************************************
     * setQuestion()
     * 
     * Arguments:
     * ..... int q:
     * ....... An Integer representing the resource
     * ....... index.
     *  
     * Notes:
     * Sets the resource index for the question
     *********************************************/
    public void setQuestion( int q){
        this.question = q;
    }
    
    /*********************************************
     * isMultChoice()
     *
     * * Arguments:
     * ..... int usersAnswer:
     * ....... An integer that represents the index
     * ....... of the user's answer.
     * 
     * Notes:
     * Returns the boolean comparison of the user's
     * answer and the correct answer.
     *********************************************/
    public boolean isMultChoice(int usersAnswer){
        return (correctAnswerIndex == usersAnswer);
    }
    
    /*********************************************
     * setMultQuestion()
     * 
     * Arguments:
     * ..... int mCIn:
     * ....... An int that represents the index value
     * ....... of what the correct answer is.
     * 
     * Notes:
     * Sets the what the correct answer needs to be
     * for this MultChoice question.
     *********************************************/
    public void setMultQuestion(int mCIn){
        this.correctAnswerIndex = mCIn;
    }
}
