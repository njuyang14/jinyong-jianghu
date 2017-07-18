import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class PreProcessingMapper extends Mapper<Object, Text, Text, Text>
{
	private Path[] localFiles;
	public void setup(Context context) throws IOException,InterruptedException
	{
		Configuration conf = context.getConfiguration();
	    localFiles = DistributedCache.getLocalCacheFiles(conf);
	    for(int i=0; i < localFiles.length; i++)
	    {
	    	StringBuffer sb= new StringBuffer("");
//	    	InputStreamReader isr = new InputStreamReader(new FileInputStream("task2/people_name_list.txt"), "utf-8");
	    	BufferedReader read = new BufferedReader(new FileReader(localFiles[i].toString()));
	    	String s=null;
	    	while((s=read.readLine())!=null)
	    	{
               if(s.trim().length()>1)
               {
            	   sb.append(s+"\t");
               }
            }
//	    	String b=sb.toString();
	    	UserDefineLibrary.insertWord(sb.toString(), "nr", 1000);
	    }
    }
	
	protected void map(Object key, Text value, Context context)throws IOException, InterruptedException
	{
		FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName().toString().split(".txt|.TXT")[0];
        Text fkey = new Text();
        fkey.set(fileName);
        String temp = new String();
        String line = value.toString().toLowerCase();
        StringTokenizer itr = new StringTokenizer(line,"\n");
        while(itr.hasMoreTokens())
        {
            temp = itr.nextToken().trim();//文件中的一段文字
            temp = temp.replaceAll("\\s*", "");
            if((!temp.equals(" "))&&(!temp.equals("\n")))
            {
            	Result terms =  ToAnalysis.parse(temp);
            	List<String> nres = new ArrayList<String>();
            	StringBuilder sval = new StringBuilder();
                for(int j=0;j<terms.size();j++)
                {
             	   Term tmp=terms.get(j);
             	   String cx=tmp.getNatureStr();
             	   if(cx.equals("nr"))
             		   nres.add(tmp.getName());
                }
                if(nres.size()!=0)
                {
                	for(int k=0;k<nres.size();k++)
                	{
                		String res_tmp=nres.get(k);
                		if(k+1<nres.size())
                			sval.append(res_tmp+" ");
                		else
                			sval.append(res_tmp);
                	}
             	   sval.append("\n");
                }
                Text val = new Text();
                val.set(sval.toString());
                context.write(fkey, val);//文件名作为key，每个段落分得的人名作为val
                /*
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
                }*/
            }
        }
    }
}
