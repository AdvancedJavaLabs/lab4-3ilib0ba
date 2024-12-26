package org.example.mapreducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyReducer extends Reducer<Text, Text, Text, Text> {
    // Выполняется для всех уникальных ключей, по которым объединены значения!
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        double totalRevenue = 0;
        int totalQuantity = 0;

        for (Text value : values) {
            String[] fields = value.toString().split(","); // Из маппера приходит <price>,<quantity>
            double price = Double.parseDouble(fields[0]);
            int quantity = Integer.parseInt(fields[1]);

            totalRevenue += price * quantity;
            totalQuantity += quantity;
        }

        context.write(key, new Text(totalRevenue + "\t" + totalQuantity));
    }
}
