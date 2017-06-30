import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by hadoop on 17-6-6.
 */
public class RTableMapper extends Mapper<Object, Text, Text, Text> {
    /***/
    public void setup(Context context) throws IOException,InterruptedException {

    }

    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        // default RecordReader: LineRecordReader; key: line offset; value: line string
        //FileSplit fileSplit = (FileSplit) context.getInputSplit();
        //String fileName = fileSplit.getPath().getName().toString().split(".txt|.TXT")[0];
        String temp = new String();
        String line = value.toString().toLowerCase();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        for(;itr.hasMoreTokens();){
            temp = itr.nextToken(); //"狄云,戚芳 1"
            if(!temp.equals(" ")) {
                Text name0 = new Text();
                Text name1 = new Text();
                String[] str = temp.split(" ");
                System.out.println(str[0]);
                System.out.println(str[1]);
                String[] name = str[0].split(",");
                System.out.println(name[0]);
                System.out.println(name[1]);
                name0.set(name[0]);
                StringBuilder out0 = new StringBuilder();
                name1.set(name[1]);
                StringBuilder out1 = new StringBuilder();
                //output: key = "name" value = "name1 num"
                context.write(name0, new Text(out0.append(name[1] +" "+ str[1]).toString()));
                context.write(name1, new Text(out1.append(name[0] +" "+ str[1]).toString()));
            }
        }
    }
}
