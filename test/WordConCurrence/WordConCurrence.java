import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 *统计在若干篇文档中两个人名在同一行中出现的次数
 * @author hmc
 */
public class WordConCurrence {

	public static void main(String[] args)
	{
		try
		{
			Configuration conf=new Configuration();
			String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
			if(otherArgs.length!=2)
			{
                System.out.println("Usage:WordConCurrence <in> <out>");
                System.exit(2);
            }
			Job job = new Job(conf, "WordConCurrence");
//			wordConcurrenceJob.setJobName("wordConcurrenceJob");
			job.setJarByClass(WordConCurrence.class);
//			wordConcurrenceJob.getConfiguration().setInt("window", Integer.parseInt(args[2]));

            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(CurrenceMap.class);
//            job.setCombinerClass(CurrenceCombiner.class);
//            job.setPartitionerClass(NewPartitioner.class);
            job.setReducerClass(CurrenceReduce.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
//			System.out.println("finished!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
