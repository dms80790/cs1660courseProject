//David Stropkey
//CS 1660 Cloud Computing
//Spring 2020
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableComparable;
//class for comparing terms
public class TermComparer extends WritableComparator {
	public TermComparer() {
		super(DocTermPair.class, true);
	}//end constructor

	@Override
	public int compare(WritableComparable term1, WritableComparable term2){
		DocTermPair pair = (DocTermPair)term1;
		DocTermPair pair2 = (DocTermPair)term2;
		int value = 0;
		
		return pair.getTerm().toString().compareTo(pair2.getTerm().toString());
	}//end compare function
}//end TermComparer class