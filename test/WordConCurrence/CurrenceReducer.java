import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class CurrenceReducer extends Reducer<Text, Text, Text, Text>
{
    public void reduce(Text key, Iterable<Text> values, Context context)throws IOException, InterruptedException
    {
    	int sum=0;
    	for(Text val:values)
    		sum++;
    	Text result=new Text();
    	result.set(Integer.toString(sum));
        context.write(key, result);
    }
}
