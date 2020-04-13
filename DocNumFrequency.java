//David Stropkey
//CS 1660 Cloud Computing
//Spring 2020
//class that pairs the frequency of the pairs with each document
public class DocNumFrequency{
	private String document;
	private int frequency;
	
	public DocNumFrequency(String docNum, int freqNum){
		document = docNum;
		frequency = freqNum;
	}//end constructor
	
	public int getFrequency(){
		return frequency;
	}//end getFrequency
	
	public String getDocument(){
		return document;
	}//end getDocument
}//end DocNumFrequency class