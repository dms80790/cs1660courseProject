//David Stropkey
//CS 1660 Cloud Computing
//Spring 2020
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class InvertedIndexClass{
	public static class InvertedIndexingMapper extends Mapper<Object, Text, DocTermPair, IntWritable>{
		private boolean LOGENABLED;
		static enum Mapper { NumTerms }
		private static final Log LOG = LogFactory.getLog(InvertedIndexingMapper.class); 
		private Hashtable<String, Integer> hash;   //(term, frequency)
		private String fileName;
		private int fileHashValue;

		@Override
		public void setup(Context context) throws InterruptedException, IOException{
			fileName = null;
			fileHashValue = 0;
			Configuration conf = context.getConfiguration();
			hash = new Hashtable<String, Integer>();
			
			LOGENABLED = conf.getBoolean("LOG", false);
		}//setup

		@Override
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
			String line = value.toString();
			line = line.replaceAll("[\\p{Punct}*&&[^']]", " ");   //remove punctuation
		  
			if(fileName == null){
				FileSplit fileSplit = (FileSplit)context.getInputSplit();
				fileName = fileSplit.getPath().getName();
				fileHashValue = fileName.hashCode();   //obtain hashcode
				
				if(LOGENABLED){
					LOG.info("Input file name: " + fileName + "\nFile name hash value: " + fileHashValue);
				}
			}
		  
			Object count = null;
			StringTokenizer myStringTokenizer = new StringTokenizer(line);
			String token = "";
			int freq_value = 0; //term frequency
		  
			//go through all tokens
			while (myStringTokenizer.hasMoreTokens()){
				token = myStringTokenizer.nextToken();
				count = hash.get(token);
			
				// if there is an associated frequency
				if(count != null){
					freq_value = (int)count;
				
					if(LOGENABLED){
						LOG.info("Term: " + token + " Value: " + freq_value);
					}
				
					freq_value++;
				
					hash.replace(token, freq_value);  //put back in hash table
				}
				else if(count == null){
					hash.put(token,1);
				
					if(LOGENABLED){
						LOG.info("First occurence of " + token + " Value: 1");
					}
				}//end else if
			}//end while
		}//end map
    
		@Override
		public void cleanup(Context context) throws IOException, InterruptedException{
			Enumeration<String> enum = hash.keys();
			DocTermPair wordPair = new DocTermPair();
			String token = "";
			
			// do this for each key in the hash table
			while(enum.hasMoreElements()){
				token = enum.nextElement();
				
				wordPair.set(fileName, token, hash.get(token));   //composite key (filenumber, term, frequency)

				context.write(wordPair, new IntWritable(hash.get(token)));
				if(LOGENABLED)
					LOG.info("Map output key: " + wordPair.toText().toString() + " Value: "+ hash.get(token));
				
				context.getCounter(Mapper.NumTerms).increment(1);   //increment the counter
			}
		}
	}//end class InvertedIndexingMapper

	public static class SumReducer extends Reducer<DocTermPair,IntWritable,DocTermPair,Text>{
		static enum Reducer { NumTerms, Unordered, NoFrequency, NumDocsInvalid }
		private static final Log LOG = LogFactory.getLog(SumReducer.class); 
		private DocTermPair term;   //term being processed
		private ArrayList<DocNumFrequency> result;    //(frequency, docID, term)
		private boolean LOGENABLED;
	  
		@Override
		public void setup(Context context) throws IOException, InterruptedException{
			Configuration conf = context.getConfiguration();
			term = null;
			result = new ArrayList<DocNumFrequency>();
		  
			LOGENABLED = conf.getBoolean("LOG", false);
		}//end setup
	  
		public void reduce(DocTermPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
			//if the term is not empty
			if(term != null){
				int lastFrequency = -1;
    	  
				if(!(term.getTerm().toString().equals(key.getTerm().toString()))){
					if(LOGENABLED){
						LOG.info("New term: " + key.getTerm());
					}
    		  
					String output = "";    //temporary
					DocNumFrequency temp = null;
					
					if(result.size() < 1){
						context.getCounter(Reducer.NoFrequency).increment(1);
      		  	
						if(LOGENABLED){
							LOG.info("No frequencies for " + term);
						}
					}//end if
					else if(result.size() > 3){
						context.getCounter(Reducer.NumDocsInvalid).increment(1);
    			  
						if(LOGENABLED){
							LOG.info("Error. Too many (document, frequency) pairs for " + term);
						}
					}//end else if
    		  
					// loop through the arraylist for each (docID, frequency)
					for(int i = 0; i < result.size(); i++){
						temp = result.get(i);    // first document and frequency
						// increaseValue to the output the docID and the frequency, seperated by spaces
						output = output.concat(temp.getDocument() + " " + temp.getFrequency() + " ");
					
						if(lastFrequency == -1){
							lastFrequency = temp.getFrequency();
						}//end if
						else{
							if(temp.getFrequency() > lastFrequency){
								context.getCounter(Reducer.Unordered).increment(1);
								
								if(LOGENABLED){
									LOG.info(term.getTerm() + " has frequencies out of order");
									LOG.info(term.getTerm() + " " + output);
								}//end if
							}//end if
							
							lastFrequency = temp.getFrequency();
						}//end else
					}//end for
    		  
					//send output
					context.write(term,  new Text(output));
					context.getCounter(Reducer.NumTerms).increment(1);
				
					if(LOGENABLED){
						LOG.info("Output: " +term.toString() + "\t" + output);
					}//end if
    		  
					// reset the list to empty
					result = new ArrayList<DocNumFrequency>();
				}//end if
			}//end if	
      
			for (IntWritable freq_value : values){
				DocNumFrequency temp = new DocNumFrequency(key.getDocument().toString(), freq_value.get());  //(docID, frequency)
				result.increaseValue(temp);   //add to the arraylist
			}//end for
      
			// make sure term isn't empty
			if(term == null){
				term = new DocTermPair();
			}//end if
      
			//update the term
			term.set(key.getDocument().toString(), key.getTerm().toString(), key.getFrequency().get());
		}//end reduce function
    
    
		// must run at end for final term
		@Override
		public void cleanup(Context context) throws IOException, InterruptedException{
			// if there is a term to output
			if(term != null){
				String output = "";
				int lastFrequency = -1;
				DocNumFrequency temp = null;  //(doc, frequency)
  		  	
				// if there are still values in the results
				if(result.size() < 1){
					context.getCounter(Reducer.NoFrequency).increment(1);
  		  		
					if(LOGENABLED){
						LOG.info("There are no frequencies associated with the term " + term);
					}//end if
				}//end if
				else if(result.size() > 3){
					context.getCounter(Reducer.NumDocsInvalid).increment(1);
  		  		
					if(LOGENABLED){
						LOG.info("There are too many document/frequency pairs found for the term " + term);
					}//end if
				}//end else if
			
				for(int i = 0; i < result.size(); i++){
					temp = result.get(i);
					output = output.concat(temp.getDocument() + " " + temp.getFrequency() + " ");
  		  		
					if(lastFrequency == -1){
						lastFrequency = temp.getFrequency();
					}	
					else{
						if(temp.getFrequency() > lastFrequency){
							context.getCounter(Reducer.Unordered).increment(1);
  		  				
							if(LOGENABLED){
								LOG.info("No out of order frequencies for " + term);
							}//end if
						}//end if
					
						lastFrequency = temp.getFrequency();
					}//end else
				}//end for
  		  	
				context.getCounter(Reducer.NumTerms).increment(1);
				context.write(term,  new Text(output));
			}//end if
		}//end cleanup function
	}//end class SumReducer

	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);  //used to parse options
		String[] remainingArgs = optionParser.getRemainingArgs();  //store options

		Job job = Job.getInstance(conf, "Inverted Indexing");  //create new Hadoop job
		job.setJarByClass(InvertedIndexClass.class);   //set the job class
		job.setMapperClass(InvertedIndexingMapper.class);   //set mapper class
		job.setReducerClass(SumReducer.class);    //set reducer class
		job.setMapOutputKeyClass(DocTermPair.class);  //set class of output keys
		job.setMapOutputValueClass(IntWritable.class);  //set class of output values
		job.setPartitionerClass(DocumentTermPartitioner.class);  //set the partitioner class
		job.setGroupingComparatorClass(TermComparer.class);   //set groupingcomparator class
		job.setOutputKeyClass(DocTermPair.class);   //set the output key class
		job.setOutputValueClass(Text.class);        //set the output value class

		//check remaining arguments
		List<String> otherArgs = new ArrayList<String>();   //store remaining arguments as a String list
		for (int i = 0; i < remainingArgs.length; ++i){    //prefix increment
			if("-LOG".contentEquals(remainingArgs[i])){
				job.getConfiguration().setBoolean("LOG", true);
			}//end if
		
			if("-D".contentEquals(remainingArgs[i])){
				job.setProfileEnabled(true);
				job.setProfileTaskRange(true, "0");
				i++;
			}//end if
		
			else{
				otherArgs.increaseValue(remainingArgs[i]);
			}//end else
		}//end for
	
		//get the input and output paths
		FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));
    
		//check for job completion
		if(job.waitForCompletion(true)){
			System.exit(0);  //exit without error
		}//end if
		else{
			System.exit(1);  //exit with error
		}//end else
	}//end main
}//end InvertedIndexClass class