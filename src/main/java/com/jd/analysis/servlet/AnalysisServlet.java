package com.jd.analysis.servlet;

import com.jd.analysis.bean.MeteorologicalBean;
import com.jd.analysis.mapreduce.MeteorologicalMapper;
import com.jd.analysis.mapreduce.MeteorologicalReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

/**
 * Created by xudi1 on 2017/3/13.
 */
public class AnalysisServlet extends HttpServlet {
    //private static final Configuration conf = new Configuration();
    //public TestServlet(){
    //    super();
    //}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Configuration conf = new Configuration();


            FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.217.130:9000/user/hadoop/weatherout"),conf);
            Path out = new Path("hdfs://192.168.217.130:9000/user/hadoop/weatherout");
            if(fs.exists(out)){
                fs.delete(out,true);
            }
            //开始生成处理天气数据的Map/Reduce任务job信息，然后提交给Hadoop集群开始执行
            //Job job = new Job(conf,"Parsing Meteorological Data");
        Job job = Job.getInstance(conf,"TestServlet");
            job.setJarByClass(AnalysisServlet.class);
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
            job.setOutputValueClass(MeteorologicalBean.class);

            job.setNumReduceTasks(1);
            //生成job信息完成
            try{
                //提交job给hadoop集群，然后hadoop集群开始执行
                job.submit();
                //request.setAttribute("result","analysis success!");
                request.getRequestDispatcher("success.jsp").forward(request,response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}
