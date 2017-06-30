import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
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

        //去重
        String temp = new String(out.toString());
        String[] all = temp.split(",");
        Set diffName = new HashSet();
        for(int i=0; i<all.length; i++)
            diffName.add(all[i]);

        out = new StringBuilder();
        out.append("1#");

        int lenEdge = 0;
        ArrayList<Person> arrayEdge = new ArrayList<Person>();//保存每条边的名字和数量
        for(Iterator it = diffName.iterator();it.hasNext();){
            String[] pair = (it.next().toString()).split(" ");
            Person person = new Person(pair[0],Integer.parseInt(pair[1]));
            arrayEdge.add(person);
            lenEdge += person.rateOfEdge;

        }
        //输出 狄云 1#戚芳 0.33,戚长发 0.33,卜垣 0.33
        for(int i = 0; i < arrayEdge.size(); i++){
            out.append(arrayEdge.get(i).name+" "+arrayEdge.get(i).rateOfEdge/lenEdge+",");
        }
        out.setLength(out.length()-1);//去掉最后的“,”
        context.write(key,new Text(out.toString()));
    }
    /**输出结果[name    pr_value#[name rate],[name rate],[name rate]...]
     卜垣	1#戚长发 0.25,戚芳 0.5,狄云 0.25
     戚芳	1#戚长发 0.25,卜垣 0.5,狄云 0.25
     戚长发	1#卜垣 0.33333334,戚芳 0.33333334,狄云 0.33333334
     狄云	1#戚长发 0.33333334,卜垣 0.33333334,戚芳 0.33333334

     */
}
