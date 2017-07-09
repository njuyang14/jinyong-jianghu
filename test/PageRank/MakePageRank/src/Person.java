import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-6-6.
 */
public class Person {
    public Person(){
        this.name = "not-initial-name";
        this.rateOfEdge = 0;
    }

    public Person(String name, float rate) {
        this.name = name;
        this.rateOfEdge = rate;
    }
    public String name;
    public float rateOfEdge;//出边数或者出边占概率

}
