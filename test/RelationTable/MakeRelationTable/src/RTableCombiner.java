import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by hadoop on 17-6-6.
 */
public class RTableCombiner extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException,InterruptedException {
        StringBuilder out = new StringBuilder();
        for(Text v:values){
            out.append(v.toString()+",");
        }
        context.write(key,new Text(out.toString()));
    }
}
