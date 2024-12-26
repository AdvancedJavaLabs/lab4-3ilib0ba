package org.example.mapreducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.example.data.CategoryCountDTO;

import java.io.IOException;

public class MyMapperSort extends Mapper<LongWritable, Text, DoubleWritable, CategoryCountDTO> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");

        String category = fields[0];
        double revenue = Double.parseDouble(fields[1]);
        int quantity = Integer.parseInt(fields[2]);
        context.write(new DoubleWritable(revenue), new CategoryCountDTO(new Text(category), new IntWritable(quantity)));
    }
}