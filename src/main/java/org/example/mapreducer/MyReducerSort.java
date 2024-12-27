package org.example.mapreducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.example.data.CategoryCountDTO;

import java.io.IOException;

public class MyReducerSort extends Reducer<DoubleWritable, CategoryCountDTO, Text, Text> {
    @Override
    protected void reduce(DoubleWritable key, Iterable<CategoryCountDTO> values, Context context)
            throws IOException, InterruptedException {
        for (CategoryCountDTO value : values) {
            context.write(value.getCategory(), new Text(key.get() + "\t" + value.getQuantity().get()));
        }
    }
}
