//David Stropkey
//CS 1660 Cloud Computing
//Spring 2020
import java.util.Enumeration;
import java.util.Hashtable;
public class WriteHashMap{
	private Hashtable<Integer,Integer> values;
	
	public WriteHashMap(){
		values = new Hashtable<Integer, Integer>();
	}//end constructor
	
	@Override
	public String toString(){
		Enumeration<Integer> enum = values.keys();
		String output = "";
		
		while(enum.hasMoreElements()){
			int key = enum.nextElement();
			output = output.concat(key + " " + values.get(key) + " ");
		}
		
		return output;
	}//end toString
	
	public void increaseValue(int key, int value){
		int freq_value = 0;
		try {
			freq_value = values.get(key);
			freq_value = (int)freq_value;
            freq_value += value;
            values.replace(key, freq_value);
		} catch(NullPointerException enum)
		{
			values.put(key, value);
        }
	}//end increaseValue
	
	public Hashtable<Integer, Integer> getHash(){
		return (Hashtable<Integer, Integer>)values.clone();
	}//end getHash
}//end WriteHashMap class