import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class CurrenceMap  extends Mapper<Object, Text, Text, IntWritable>
{
	private final static IntWritable val=new IntWritable(1);
	protected void map(Object key, Text value, Context context)throws IOException, InterruptedException
	{
        String temp = new String();
        String line = value.toString();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        while(itr.hasMoreTokens())
        {
            temp = itr.nextToken(); //"狄云 戚芳 卜垣 老孙"
            if((!temp.equals(" "))&&(!temp.equals("\n")))
            {
                Text name0 = new Text();
                String[] str = temp.split(" ");
                int size=str.length;
                for(int i=0; i<size; i++)
                {
                	String name1=str[i];
                	for(int j=0; j<size; j++)
                	{
                		String name2=str[j];
                		if(name2.equals(name1))
                			continue;
                		String r=name1+","+name2;
                		name0.set(r);
//                		StringBuilder out = new StringBuilder();
                		//output: key = "name1,name2" value = "1"
                		context.write(name0, val);
                	}
                }
            }
        }
    }
}
