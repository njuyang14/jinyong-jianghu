import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class SortReducer extends Reducer<DoubleWritable, Text, Text, Text>
{
	public void reduce(DoubleWritable key, Iterable<Text> values, Context context)throws IOException, InterruptedException
	{
        for(Text v:values)
        {
        	Text val=new Text();
        	val.set(key.toString());
        	context.write(v, val);
        }
	}
}
