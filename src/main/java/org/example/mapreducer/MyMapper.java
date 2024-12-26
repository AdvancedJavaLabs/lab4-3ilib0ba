package org.example.mapreducer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
    // Вызывается для каждой строки
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (key.get() == 0 && line.contains("transaction_id")) {
            return;
        }

        String[] fields = line.split(",");
        if (fields.length == 5) {
            String category = fields[2];
            double price = Double.parseDouble(fields[3]);
            int quantity = Integer.parseInt(fields[4]);
            context.write(new Text(category), new Text(price + "," + quantity));
        }
    }
}
