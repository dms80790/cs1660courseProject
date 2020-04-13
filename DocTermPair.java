//David Stropkey
//CS 1660 Cloud Computing
//Spring 2020
import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.IntWritable;
public class DocTermPair implements WritableComparable<DocTermPair>, Writable{
  private Text term = new Text();
  private Text document = new Text();
  private IntWritable frequency = new IntWritable();
  
  @Override
  /* This comparator controls the sort order of the keys. */
  public int compareTo(DocTermPair pair){
	  int value = this.term.toString().compareTo(pair.getTerm().toString());
	  if(value == 0){
		  value = Integer.valueOf(frequency.get()).compareTo(Integer.valueOf(pair.getFrequency().get()));
	  }
	  return value * -1;
  }//end compareTo
  
  public Text getDocument(){
	  return this.document;
  }//end getDocument
  
  public IntWritable getFrequency(){
	  return this.frequency;
  }//end getFrequency
  
  public Text getTerm(){
	  return this.term;
  }//end getTerm
  
  public void set(String docNum, String word, int frequency){
	  term.set(word);
	  document.set(docNum);
	  frequency.set(frequency);
  }//end set
  
  public IntWritable getFrequency(){
	  return this.frequency;
  }//end getFrequency
  
  public void readFields(DataInput in) throws IOException{
	  term.readFields(in);
	  frequency.readFields(in);
	  document.readFields(in);
  }//end readFields
  
  public void write(DataOutput out) throws IOException{
	  this.term.write(out);
	  this.frequency.write(out);
	  this.document.write(out);
  }//end write
  
  public String toString(){
	  return this.term.toString();
  }//end toString
  
  public Text toText(){
	  Text temp = new Text();
	  temp.set(this.document + " " + this.term);
	  return temp;
  }//end toText
}//end DocTermPair class