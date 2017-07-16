import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.StringTokenizer;

public class SortMapper extends Mapper<Object, Text, DoubleWritable, Text> 
{
	protected void map(Object key, Text value, Context context)throws IOException, InterruptedException
	{
        String temp = new String();
        String line = value.toString().toLowerCase();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        for(;itr.hasMoreTokens();)
        {
            temp = itr.nextToken(); //"解索	0.18624517416687714#萧峰 1.0"
            if(!temp.equals(" "))
            {                
                String[] str = temp.split("#");
                String[] sort = str[0].split("\t");
                DoubleWritable pr = new DoubleWritable(Double.parseDouble(sort[1]));
                Text val =new Text(sort[0]);
                //output: key = pr value = "name"
                context.write(pr, val);
            }
        }
    }
}
