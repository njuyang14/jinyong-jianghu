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
     *"卜垣   1#戚长发 0.25,戚芳 0.5,狄云 0.25"
     */
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        // default RecordReader: LineRecordReader; key: line offset; value: line string
        String temp = new String();
        String line = value.toString().toLowerCase();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        for(;itr.hasMoreTokens();){
            temp = itr.nextToken(); //"卜垣   1#戚长发 0.25,戚芳 0.5,狄云 0.25"
            if(!temp.equals(" ")) {
                String[] input = temp.split("\t");
                String pageKey = input[0];//卜垣
                String prAndLink[] = input[1].split("#");
                double rank = Double.parseDouble(prAndLink[0]);//1
                String[] linkPerson = prAndLink[1].split(",");//[戚长发 0.25][戚芳 0.5][狄云 0.25]...
                for(int i=0;i<linkPerson.length;i++){
                    String[] pair = linkPerson[i].split(" ");
                    Person person = new Person(pair[0],Float.parseFloat(pair[1]));
                    context.write(new Text(person.name),new Text(Double.toString(rank*person.rateOfEdge)));
                }
                context.write(new Text(pageKey),new Text("|"+input[1]));
            }
        }

    }
}
