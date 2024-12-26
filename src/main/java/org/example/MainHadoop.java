package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.example.data.CategoryCountDTO;
import org.example.mapreducer.*;

import java.io.IOException;

public class MainHadoop {
    // Проверил, что можно указать каталог -- тогда пройдется по всем файлам
    public static String dirWithData;
    public static String dirWithResults;
    public static String dirWithResultsSorted;
    public static int reducersCount;
    public static int blockSize;

    public static Configuration conf;

    public static void main(String[] args) throws Exception {
        parseConsoleArgs(args);
        createAndSetUpConfigurationHadoop();

        long startTime = System.currentTimeMillis();
        Job job1 = createJobMapAndRevenue();
        if (job1.waitForCompletion(true)) {
            System.out.println("Complete TOPIC_FOR_REVENUE_AND_COUNT success, time required: " + (System.currentTimeMillis() - startTime));
        }

        startTime = System.currentTimeMillis();
        Job job2 = createJobRevenueSort();
        if (job2.waitForCompletion(true)) {
            System.out.println("Complete TOPIC_FOR_REVENUE_SORT success, time required: " + (System.currentTimeMillis() - startTime));
        }

    }

    private static void parseConsoleArgs(String[] args) {
        if (args.length != 4) {
            // Пример моей команды (вызывается из каталога репозитория):
            // hadoop jar ./target/myhadoopslave-1.jar org.example.MainHadoop ./input ./output 2 1
            System.err.println("HAVE TO INPUT 4 ARGS: <PATH_TO_INPUT_FILE_IN_DFS> <PATH_TO_OUTPUT_FILE_IN_DFS> " +
                    "<COUNT_OF_REDUCERS> <SIZE_OF_BLOCS_IN_MB>");
            System.exit(-1);
        }
        // Сохраним данные
        dirWithData = args[0];
        dirWithResults = args[1];
        dirWithResultsSorted = dirWithResults + "_sorted";
        reducersCount = Integer.parseInt(args[2]);
        blockSize = Integer.parseInt(args[3]) * ((int) Math.pow(2, 20)); // заданный размер * 1 МБ
    }

    private static void createAndSetUpConfigurationHadoop() {
        conf = new Configuration();

        conf.set("mapreduce.map.memory.mb", "1"); // 1 мегабайт максимум для каждого маппера
        conf.set("mapreduce.reduce.memory.mb", "2"); // 2 мегабайта для редьюсера (максимально для каждого)
        conf.set("mapreduce.map.java.opts", "-Xmx512m"); // максимально выделенной оперативы 512 мегабайт (чтобы не переписывать кучу временных файлов в память hdfs)
        conf.set("mapreduce.reduce.java.opts", "-Xmx128m"); // ???
        conf.set("mapreduce.input.fileinputformat.split.maxsize", Integer.toString(blockSize));
    }

    private static Job createJobMapAndRevenue() throws IOException {
        Job job = Job.getInstance(conf, "TOPIC_FOR_REVENUE_AND_COUNT");
        job.setJarByClass(MainHadoop.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.addInputPath(job, new Path(dirWithData));
        FileOutputFormat.setOutputPath(job, new Path(dirWithResults));

        return job;
    }

    private static Job createJobRevenueSort() throws IOException {
        Job job = Job.getInstance(conf, "TOPIC_FOR_REVENUE_SORTED");
        job.setJarByClass(MainHadoop.class);
        job.setMapperClass(MyMapperSort.class);
        job.setReducerClass(MyReducerSort.class);
        job.setOutputKeyClass(DoubleWritable.class); // Важно поменять на этот класс, иначе падает, т.к. ожидает другой тип
        job.setOutputValueClass(CategoryCountDTO.class); // Тут тоже

        job.setSortComparatorClass(MyRevenueComparator.class);

        FileInputFormat.addInputPath(job, new Path(dirWithResults));
        FileOutputFormat.setOutputPath(job, new Path(dirWithResultsSorted));

        return job;
    }


}
