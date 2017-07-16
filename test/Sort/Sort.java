import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Sort {

	public static class DoubleWritableDecreasingComparator extends DoubleWritable.Comparator
	{
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2)
		{  
			return -super.compare(b1, s1, l1, b2, s2, l2);
		}
	}  
	
	public static void main(String[] args)
	{
		 try {
	            Configuration conf=new Configuration();//从hadoop配置文件中读取参数
	            //从命令行读取参数
	            String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();

	            if(otherArgs.length!=2)
	            {
	                System.out.println("Usage:Sort <in> <out>");
	                System.exit(2);
	            }

	            Job job = new Job(conf, "Sort");
	            job.setJarByClass(Sort.class);
	            job.setInputFormatClass(TextInputFormat.class);
	            job.setMapperClass(SortMapper.class);
	            job.setReducerClass(SortReducer.class);
	            job.setMapOutputKeyClass(DoubleWritable.class);
	            job.setMapOutputValueClass(Text.class);
	            job.setOutputKeyClass(Text.class);
	            job.setOutputValueClass(Text.class);
	            FileInputFormat.addInputPath(job, new Path(args[0]));
	            FileOutputFormat.setOutputPath(job, new Path(args[1]));
	            job.setSortComparatorClass(DoubleWritableDecreasingComparator.class);//设置Sort阶段使用比较器 
	            System.exit(job.waitForCompletion(true) ? 0 : 1);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
