import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hadoop on 17-6-6.
 */
public class RTableReducer extends Reducer<Text, Text, Text, Text> {
    private String term = new String();

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        StringBuilder out = new StringBuilder();//临时存储输出的value部分
        for(Text v:values){
            out.append(v.toString()+",");
        }
        out.setLength(out.length()-1);//去掉最后的“,”
        String temp = new String(out.toString());
        String[] all = temp.split(",");
        Set diffName = new HashSet();
        for(int i=0; i<all.length; i++)
            diffName.add(all[i]);
        out = new StringBuilder();
        for(Iterator it = diffName.iterator();it.hasNext();){
            out.append(it.next().toString()+",");
        }
        out.setLength(out.length()-1);//去掉最后的“,”
        context.write(key,new Text(out.toString()));
    }
}
