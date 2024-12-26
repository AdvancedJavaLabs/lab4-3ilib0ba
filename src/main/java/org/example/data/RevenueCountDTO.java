package org.example.data;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class RevenueCountDTO implements Writable {
    private DoubleWritable revenue;
    private IntWritable quantity;

    public RevenueCountDTO() {
        this.revenue = new DoubleWritable();
        this.quantity = new IntWritable();
    }

    public RevenueCountDTO(double revenue, int quantity) {
        this.revenue = new DoubleWritable(revenue);
        this.quantity = new IntWritable(quantity);
    }

    public void set(double revenue, int quantity) {
        this.revenue.set(revenue);
        this.quantity.set(quantity);
    }

    public double getRevenue() {
        return this.revenue.get();
    }

    public int getQuantity() {
        return this.quantity.get();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        revenue.write(out);
        quantity.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        revenue.readFields(in);
        quantity.readFields(in);
    }

    @Override
    public String toString() {
        return revenue + "\t" + quantity;
    }
}
