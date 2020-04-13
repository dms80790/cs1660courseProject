//David Stropkey
//CS 1660 Cloud Computing
//Spring 2020
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.io.IntWritable;
//partitioner using hashing to ensure unique values for each partition
public class DocumentTermPartitioner extends Partitioner<DocTermPair, IntWritable>{
	@Override
	public int getPartition(DocTermPair pair, IntWritable frequency, int numberOfPartitions){
		return Math.abs(pair.getTerm().hashCode() % numberOfPartitions);
	}//end getPartition
}//end DocumentTermPartitioner class