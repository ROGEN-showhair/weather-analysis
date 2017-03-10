package com.jd.analysis.servelt;

import com.jd.analysis.bean.MeteorologicalBean;
import com.jd.analysis.bean.UserBean;
import com.jd.analysis.mapreduce.MeteorologicalMapper;
import com.jd.analysis.mapreduce.MeteorologicalReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Progressable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class WeatherParsingServelt extends HttpServlet {

    private static final long serialVersionUID = 151272610672487725L;
    private static final Configuration conf = new Configuration();
    public WeatherParsingServelt(){
        super();
    }

    /**
     * 处理用户提交的天气数据云计算分析，用户提交了分析的命令以后，该方法会向Hadoop提交一个
     * Map/Reduce任务，用于执行天气分析的任务
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserBean ub = (UserBean)request.getSession().getAttribute("user");
        if(ub == null){
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }else if(ub.getUserId().equals("admin")){
            FileSystem fs = FileSystem.get(conf);
            Path out = new Path("/tomcat/experiment/weatherclout/results");
            if(fs.exists(out)){
                fs.delete(out,true);
            }
            //开始生成处理天气数据的Map/Reduce任务job信息，然后提交给Hadoop集群开始执行
            Job job = new Job(conf,"Parsing Meteorological Data");
            job.setJarByClass(WeatherParsingServelt.class);
            Path in = new Path("/tomcat/experiment/weathercloud/uploaddata");
            FileInputFormat.setInputPaths(job,in);
            FileOutputFormat.setOutputPath(job,out);

            job.setMapperClass(MeteorologicalMapper.class);
            job.setReducerClass(MeteorologicalReducer.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(MeteorologicalBean.class);

            job.setNumReduceTasks(1);
            //生成job信息完成
            try{
                //提交job给hadoop集群，然后hadoop集群开始执行
                job.submit();
                request.getRequestDispatcher("mrlink.jsp").forward(request,response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
