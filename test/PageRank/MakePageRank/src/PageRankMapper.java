import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by hadoop on 17-6-6.
 */
public class PageRankMapper extends Mapper<Object, Text, Text, Text> {
    /***/
    public void setup(Context context) throws IOException,InterruptedException {

    }
    /**
     *输入文件格式：
     *"狄云 1#戚芳,3 戚长发,2 卜垣,1"
     */
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        // default RecordReader: LineRecordReader; key: line offset; value: line string
        String temp = new String();
        String line = value.toString().toLowerCase();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        for(;itr.hasMoreTokens();){
            temp = itr.nextToken(); //"狄云,戚芳 1"
            if(!temp.equals(" ")) {

            }
        }
    }
}
