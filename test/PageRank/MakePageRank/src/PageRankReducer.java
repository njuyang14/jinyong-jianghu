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

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

    }
}
