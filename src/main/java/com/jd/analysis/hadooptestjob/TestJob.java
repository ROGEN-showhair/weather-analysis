package com.jd.analysis.hadooptestjob;

import com.jd.analysis.bean.MeteorologicalBean;
import com.jd.analysis.mapreduce.MeteorologicalMapper;
import com.jd.analysis.mapreduce.MeteorologicalReducer;
import com.jd.analysis.servelt.WeatherParsingServelt;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * Created by xudi1 on 2017/3/13.
 */
public class TestJob {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path out = new Path("hdfs://192.168.217.130:9000/user/hadoop/weatherout");
        if(fs.exists(out)){
            fs.delete(out,true);
        }
        //开始生成处理天气数据的Map/Reduce任务job信息，然后提交给Hadoop集群开始执行
        Job job = new Job(conf,"Parsing Meteorological Data");
        job.setJarByClass(TestJob.class);
        Path in = new Path("hdfs://192.168.217.130:9000/user/hadoop/input/data");
        FileInputFormat.setInputPaths(job,in);
        FileOutputFormat.setOutputPath(job,out);

        job.setMapperClass(MeteorologicalMapper.class);
        job.setReducerClass(MeteorologicalReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(MeteorologicalBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        //job.setNumReduceTasks(1);
        //生成job信息完成
        try {
            //提交job给hadoop集群，然后hadoop集群开始执行
            job.submit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
