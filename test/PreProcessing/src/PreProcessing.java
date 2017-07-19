import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.net.URI;

public class PreProcessing {

	public static void main(String[] args) throws Exception
	{
		try
		{
			Configuration conf=new Configuration();
			conf.set("mapred.textoutputformat.ignoreseparator", "true");
			DistributedCache.addCacheFile(new URI("./data/people_name_list.txt"),conf);//人名表
			String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
			if(otherArgs.length!=2)
			{
                System.out.println("Usage:PreProcessing <in> <out>");
                System.exit(2);
            }
			Job job = new Job(conf, "PreProcessing");
			job.setJarByClass(PreProcessing.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(PreProcessingMapper.class);
            job.setReducerClass(PreProcessingReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(NullWritable.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
