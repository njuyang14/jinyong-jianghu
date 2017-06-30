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
public class PageRankReducer extends Reducer<Text, Text, Text, Text> {
    private String term = new String();
    private double damping = 0.85;
    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        String links = "";
        double pagerank = 0;
        for(Text value : values){
            String tmp = value.toString();//
            if(tmp.startsWith("|")){
                links = "\t"+tmp.substring(tmp.indexOf("|")+1);
                links = links.split("#")[1];
                continue;
            }
            pagerank += Double.parseDouble(tmp);
        }
        pagerank = (double)(1-damping) + damping*pagerank;
        context.write(new Text(key),new Text(String.valueOf(pagerank)+"#"+links));
    }
    /**
     卜垣	1.141666678#戚长发 0.25,戚芳 0.5,狄云 0.25
     戚芳	1.141666678#戚长发 0.25,卜垣 0.5,狄云 0.25
     戚长发	0.8583333390000001#卜垣 0.33333334,戚芳 0.33333334,狄云 0.33333334
     狄云	0.8583333390000001#戚长发 0.33333334,卜垣 0.33333334,戚芳 0.33333334
     */
}
