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
 * Created by hadoop on 17-6-6.
 */
public class RelationTable {
    public static void main(String []args){
        //set job
        try {
            Configuration conf=new Configuration();//从hadoop配置文件中读取参数
            //从命令行读取参数
            String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();

            if(otherArgs.length!=2){
                System.out.println("Usage:invertIndex <in> <out>");
                System.exit(2);
            }

            Job job = new Job(conf, "RelationTable");
            job.setJarByClass(RelationTable.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setMapperClass(RTableMapper.class);
            job.setCombinerClass(RTableCombiner.class);
            //job.setPartitionerClass(NewPartitioner.class);
            job.setReducerClass(RTableReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
