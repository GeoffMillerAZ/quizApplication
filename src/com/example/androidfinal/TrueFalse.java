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
 *  This file contains the implementation of the TrueFalse class
 ************************************************************************/
package com.example.androidfinal;

/************************************************************************
*  Class: TrueFalse
*  
*  Notes:
*  This class contains members and functions necessary to create a
*  True or False question and then validate a user's answer vs. the
*  correct answer.
************************************************************************/
public class TrueFalse {
	
	int question;
	//this is a resource id for the question
	boolean trueQuestionAnswer;
	
	/*********************************************
	 * CONSTRUCTOR
	 * 
	 * Arguments:
	 * ..... int q:
	 * ....... An Integer representing the resource
	 * ....... index.
	 *  
	 * ......boolean tQ:
	 * ....... A Boolean representing the correct
	 * ....... answer to the question being constructed
	 * 
	 * Notes:
	 * This constructor creates an instance of TrueFalse
	 * question and sets the resource index and correct
	 * answer.
	 *********************************************/
	public TrueFalse( int q, boolean tQ){		
		question = q;
		trueQuestionAnswer = tQ;			
	} // End Constructor
	
	
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
     * isTrueQuestion()
     * 
     * Notes:
     * Returns the what the answer is for this TrueFalse
     * question.
     *********************************************/
	public boolean isTrueQuestion(){
		return trueQuestionAnswer;
	}
	
	/*********************************************
     * setTrueQuestion()
     * 
     * Arguments:
      * ......boolean tQ:
     * ....... A Boolean representing the correct
     * ....... answer to the question being constructed
     * 
     * Notes:
     * Sets the what the correct answer needs to be
     * for this TrueFalse question.
     *********************************************/
	public void setTrueQuestion(boolean tQ){
		this.trueQuestionAnswer = tQ;
	}
	
}