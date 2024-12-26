package org.example.data;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CategoryCountDTO implements Writable {
    private Text category;
    private IntWritable quantity;

    public CategoryCountDTO() {
        this.category = new Text();
        this.quantity = new IntWritable();
    }

    public CategoryCountDTO(Text category, IntWritable quantity) {
        this.category = category;
        this.quantity = quantity;
    }

    public Text getCategory() {
        return category;
    }

    public void setCategory(Text category) {
        this.category = category;
    }

    public IntWritable getQuantity() {
        return quantity;
    }

    public void setQuantity(IntWritable quantity) {
        this.quantity = quantity;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        category.write(out);
        quantity.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        category.readFields(in);
        quantity.readFields(in);
    }

    @Override
    public String toString() {
        return category + "\t" + quantity;
    }
}
