[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/-hH64FG6)
# Лабораторная работа: Реализация MapReduce для анализа данных о продажах с ипользованием HADOOP!!!
## Цель работы

Ознакомиться с концепцией распределенных вычислений на примере модели MapReduce. Научиться разрабатывать многопоточную систему для обработки больших данных и применять её для анализа данных о продажах.
## Описание задачи

У вас в репозитории есть несколько CSV-файлов, представляющих данные о продажах, например:

    transaction_id,product_id,category,price,quantity
    1,101,electronics,300.00,2
    2,102,books,15.00,5
    3,101,electronics,300.00,1
    4,103,toys,25.00,4
    5,102,books,15.00,3

Необходимо:

  * Вычислить общую выручку для каждой категории товаров.
  * Подсчитать общее количество проданных товаров по категориям.
  * Отсортировать категории по общей выручке в порядке убывания.

Пример вывода:

    Category      Revenue    Quantity
    electronics   900.00     3
    books         120.00     8
    toys          100.00     4

## Требования
Основная часть:

  * Используем hadoop
  * Написать реализацию MapReduce для обработки CSV-файлов.
  * Реализовать многопоточность в каждой фазе:
      * Map — обработка строк из файлов.
      * Shuffle/Sort — группировка данных по категориям.
      * Reduce — вычисление итоговых значений для каждой категории.
  * Сохранить результат в файл.
  * Обеспечить потокобезопасность при работе с общими данными.
  * Реализовать поддержку одновременной обработки большого количества файлов.

Дополнительные задачи (по желанию):

* Добавить возможность выбора метрики анализа (например, подсчёт средней цены товара в категории).

## Результаты
Результатом работы является сам код, файл с результатами и экспериментальные данные по быстродействию работы написанного кода при изменении числа worker-ов / частей, на которые разбивается файл

# Ход выполнения работы и полученные результаты

## Пример вывода информации после запуска программы

```
2024-12-26 05:32:49,539 INFO client.RMProxy: Connecting to ResourceManager at localhost/127.0.0.1:8032
2024-12-26 05:32:49,784 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
2024-12-26 05:32:49,806 INFO mapreduce.JobResourceUploader: Disabling Erasure Coding for path: /tmp/hadoop-yarn/staging/evonn/.staging/job_1735179825322_0003
2024-12-26 05:32:49,859 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:32:49,933 INFO input.FileInputFormat: Total input files to process : 1
2024-12-26 05:32:49,955 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:32:50,390 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:32:50,402 INFO mapreduce.JobSubmitter: number of splits:1
2024-12-26 05:32:50,465 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:32:50,477 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1735179825322_0003
2024-12-26 05:32:50,477 INFO mapreduce.JobSubmitter: Executing with tokens: []
2024-12-26 05:32:50,575 INFO conf.Configuration: resource-types.xml not found
2024-12-26 05:32:50,576 INFO resource.ResourceUtils: Unable to find 'resource-types.xml'.
2024-12-26 05:32:50,612 INFO impl.YarnClientImpl: Submitted application application_1735179825322_0003
2024-12-26 05:32:50,638 INFO mapreduce.Job: The url to track the job: http://DESKTOP-3I69DUF:8088/proxy/application_1735179825322_0003/
2024-12-26 05:32:50,638 INFO mapreduce.Job: Running job: job_1735179825322_0003
2024-12-26 05:32:55,738 INFO mapreduce.Job: Job job_1735179825322_0003 running in uber mode : false
2024-12-26 05:32:55,740 INFO mapreduce.Job:  map 0% reduce 0%
2024-12-26 05:32:59,812 INFO mapreduce.Job:  map 100% reduce 0%
2024-12-26 05:33:03,864 INFO mapreduce.Job:  map 100% reduce 100%
2024-12-26 05:33:03,870 INFO mapreduce.Job: Job job_1735179825322_0003 completed successfully
2024-12-26 05:33:03,926 INFO mapreduce.Job: Counters: 50
        File System Counters
                FILE: Number of bytes read=6
                FILE: Number of bytes written=454851
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
                HDFS: Number of bytes read=240
                HDFS: Number of bytes written=0
                HDFS: Number of read operations=8
                HDFS: Number of large read operations=0
                HDFS: Number of write operations=2
                HDFS: Number of bytes read erasure-coded=0
        Job Counters
                Launched map tasks=1
                Launched reduce tasks=1
                Data-local map tasks=1
                Total time spent by all maps in occupied slots (ms)=1879
                Total time spent by all reduces in occupied slots (ms)=1801
                Total time spent by all map tasks (ms)=1879
                Total time spent by all reduce tasks (ms)=1801
                Total vcore-milliseconds taken by all map tasks=1879
                Total vcore-milliseconds taken by all reduce tasks=1801
                Total megabyte-milliseconds taken by all map tasks=1924096
                Total megabyte-milliseconds taken by all reduce tasks=1844224
        Map-Reduce Framework
                Map input records=3
                Map output records=0
                Map output bytes=0
                Map output materialized bytes=6
                Input split bytes=108
                Combine input records=0
                Combine output records=0
                Reduce input groups=0
                Reduce shuffle bytes=6
                Reduce input records=0
                Reduce output records=0
                Spilled Records=0
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=73
                CPU time spent (ms)=0
                Physical memory (bytes) snapshot=0
                Virtual memory (bytes) snapshot=0
                Total committed heap usage (bytes)=665845760
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Input Format Counters
                Bytes Read=132
        File Output Format Counters
                Bytes Written=0
Jobs completed in 15032 milliseconds.
2024-12-26 05:33:03,950 INFO client.RMProxy: Connecting to ResourceManager at localhost/127.0.0.1:8032
2024-12-26 05:33:03,955 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
2024-12-26 05:33:03,972 INFO mapreduce.JobResourceUploader: Disabling Erasure Coding for path: /tmp/hadoop-yarn/staging/evonn/.staging/job_1735179825322_0004
2024-12-26 05:33:03,982 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:33:04,002 INFO input.FileInputFormat: Total input files to process : 1
2024-12-26 05:33:04,009 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:33:04,023 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:33:04,035 INFO mapreduce.JobSubmitter: number of splits:1
2024-12-26 05:33:04,047 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2024-12-26 05:33:04,058 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1735179825322_0004
2024-12-26 05:33:04,058 INFO mapreduce.JobSubmitter: Executing with tokens: []
2024-12-26 05:33:04,294 INFO impl.YarnClientImpl: Submitted application application_1735179825322_0004
2024-12-26 05:33:04,301 INFO mapreduce.Job: The url to track the job: http://DESKTOP-3I69DUF:8088/proxy/application_1735179825322_0004/
2024-12-26 05:33:04,301 INFO mapreduce.Job: Running job: job_1735179825322_0004
2024-12-26 05:33:14,454 INFO mapreduce.Job: Job job_1735179825322_0004 running in uber mode : false
2024-12-26 05:33:14,454 INFO mapreduce.Job:  map 0% reduce 0%
2024-12-26 05:33:18,522 INFO mapreduce.Job:  map 100% reduce 0%
2024-12-26 05:33:22,581 INFO mapreduce.Job:  map 100% reduce 100%
2024-12-26 05:33:22,587 INFO mapreduce.Job: Job job_1735179825322_0004 completed successfully
2024-12-26 05:33:22,613 INFO mapreduce.Job: Counters: 50
        File System Counters
                FILE: Number of bytes read=6
                FILE: Number of bytes written=454915
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
                HDFS: Number of bytes read=129
                HDFS: Number of bytes written=0
                HDFS: Number of read operations=8
                HDFS: Number of large read operations=0
                HDFS: Number of write operations=2
                HDFS: Number of bytes read erasure-coded=0
        Job Counters
                Launched map tasks=1
                Launched reduce tasks=1
                Other local map tasks=1
                Total time spent by all maps in occupied slots (ms)=1879
                Total time spent by all reduces in occupied slots (ms)=1833
                Total time spent by all map tasks (ms)=1879
                Total time spent by all reduce tasks (ms)=1833
                Total vcore-milliseconds taken by all map tasks=1879
                Total vcore-milliseconds taken by all reduce tasks=1833
                Total megabyte-milliseconds taken by all map tasks=1924096
                Total megabyte-milliseconds taken by all reduce tasks=1876992
        Map-Reduce Framework
                Map input records=0
                Map output records=0
                Map output bytes=0
                Map output materialized bytes=6
                Input split bytes=129
                Combine input records=0
                Combine output records=0
                Reduce input groups=0
                Reduce shuffle bytes=6
                Reduce input records=0
                Reduce output records=0
                Spilled Records=0
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=82
                CPU time spent (ms)=0
                Physical memory (bytes) snapshot=0
                Virtual memory (bytes) snapshot=0
                Total committed heap usage (bytes)=666894336
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Input Format Counters
                Bytes Read=0
        File Output Format Counters
                Bytes Written=0

```

## Команды для работы с Hadoop

```shell
hdfs dfs -copyFromLocal mycsv/0.csv mycsv/1.csv ... input/

hdfs dfs -rm -r ./output*

hadoop jar ./target/myhadoopslave-1.jar org.example.MainHadoop /user/evonn/input/@.csv /user/evonn/output 1 1
```