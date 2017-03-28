package com.jd.analysis.data;

import com.jd.analysis.bean.MonthBean;
import com.jd.analysis.graph.DrawLineChart;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.jfree.data.category.CategoryDataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xudi1 on 2017/3/13.
 */
public class ReadResult {
    private static String reduce_success = "hdfs://192.168.217.130:9000/user/hadoop/weatherout/_SUCCESS";
    private static String reduce_result = "hdfs://192.168.217.130:9000/user/hadoop/weatherout/part-r-00000";


    public static List<MonthBean> Read() {
        List<MonthBean> monList = new LinkedList<MonthBean>();
        try {
            Configuration conf = new Configuration();
            FileSystem fileSystem = FileSystem.get(URI.create(reduce_result),conf);
            Path result = new Path(reduce_result);
            if (fileSystem.exists(result)) {
                FSDataInputStream fsdis = fileSystem.open(result);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
                String line = null;
                int count = 0;
                float maxTempTotal = 0.0f;
                float minTempTotal = 0.0f;
                float humidityTotal = 0.0f;
                float WSPTotal = 0.0f;
                //2012-01
                //AVG { Temp ( max: asdfasfd^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //开始汇总读取到的每一行数据文件，主要的操作是把所有结果汇总，然后返回给客户端
                while((line = reader.readLine()) != null){
                    monList.add(ReadLine(line));
                }
                fsdis.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return monList;
    }

    /**
     * 把result中每一行都封装进一个MonthBean
     * @return
     */
    public static MonthBean ReadLine(String line){
        MonthBean monBean = new MonthBean();
        //把每行数据按Tab读取到array中
        String[] array = line.split("\t");
        //如果数据完整。。
        if(array.length == 2){
           // MonthBean monBean = new MonthBean();
            monBean.setMonth(array[0]);
            String value = array[1] .substring(
                    array[1].indexOf("{") + 1,
                    array[1].indexOf("}"));
            String[] metes = value.split(";");
            if(metes.length == 3){
                try{
                    String[] temps = metes[0].substring(
                            metes[0].indexOf("(") + 1,
                            metes[0].indexOf(")")).split("/");
                    if(temps.length == 2){
                        monBean.setMaxTemp(Float.parseFloat(temps[0].substring(
                                temps[0].indexOf("max:") + "max:".length(),
                                temps[0].indexOf("℃"))));
                        //maxTempTotal += monBean.getMaxTemp();
                        monBean.setMinTemp(Float.parseFloat(temps[1].substring(
                                temps[1].indexOf("min:") + "min:".length(),
                                temps[1].indexOf("℃"))));
                        //minTempTotal += monBean.getMinTemp();
                    }
                    String humidity = metes[1].substring(
                            metes[1].indexOf("(") + 1,
                            metes[1].indexOf(")"));
                    monBean.setHumidity(Float.parseFloat(humidity.substring(0,
                            humidity.indexOf("%"))));
                    //humidityTotal += monBean.getHumidity();
                    String WSP = metes[2].substring(
                            metes[2].indexOf("(") + 1,
                            metes[2].indexOf(")"));
                    monBean.setWSP(Float.parseFloat(WSP.substring(0,
                            WSP.indexOf("m/s"))));
                    //WSPTotal += monBean.getWSP();
                    //count++;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //System.out.println(array[0]);
            //System.out.println(array[0].split("-")[1] + "\t" + monBean.toString());   //显示的是几月
            System.out.println(monBean.toString());
        }
        return monBean;
    }
}



