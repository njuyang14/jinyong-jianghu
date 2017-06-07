import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-6-6.
 */
public class Person implements Writable {
    public Person(String name, float rate) {
        this.name = name;
        this.rateOfEdge = rate;
    }
    public String name;
    public float rateOfEdge;//出边数或者出边占概率

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.name);
        dataOutput.writeFloat(this.rateOfEdge);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.name = dataInput.readUTF();
        this.rateOfEdge = dataInput.readFloat();
    }

}
