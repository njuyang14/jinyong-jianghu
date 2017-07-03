import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class CurrenceMapper  extends Mapper<Object, Text, Text, Text>
{
	protected void map(Object key, Text value, Context context)throws IOException, InterruptedException
	{
        String temp = new String();
        String line = value.toString().toLowerCase();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        while(itr.hasMoreTokens())
        {
            temp = itr.nextToken(); //"狄云 戚芳 卜垣 老孙"
            if((!temp.equals(" "))&&(!temp.equals("\n")))
            {
                Text name0 = new Text();
                Text val = new Text();
                String[] tmpstr = temp.split(" ");
                Set<String> str = new HashSet<String>(Arrays.asList(tmpstr));
                int size=str.size();
                for(Iterator<String> i=str.iterator(); i.hasNext();)
                {
                	String name1=i.next();
                	for(Iterator<String> j=str.iterator(); j.hasNext();)
                	{
                		String name2=j.next();
                		if(name2.equals(name1))
                			continue;
                		String r=name1+","+name2;
                		name0.set(r);
                		val.set("1");
                		//output: key = "name1,name2" value = "1"
                		context.write(name0, val);
                	}
                }
            }
        }
    }
}
