package com.jd.analysis.mapreduce;

import com.jd.analysis.bean.MeteorologicalBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class MeteorologicalMapper extends Mapper<LongWritable,Text,Text,MeteorologicalBean>{

    public static enum Counters{
        ROWS
    }
    /**
     * 读取一行数据后，该方法就开始处理
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //按行读取数据:以\t为分隔符，存到array中
        String[] array = value.toString().split("\t");
        //如果读入数据完整：Time  Temp{max:/min:};Humidity{};WSP{}
        if(array.length == 2){
            //把后面的值保存到 metes 中： Temp{max:/min:};Humidity{};WSP{}
            String[] metes = array[1].split(";");
            //如果metes数据完整：Temp{max:/min:};Humidity{};WSP{}
            if(metes.length == 3){
                try{
                    String[] temps = metes[0].substring(
                            metes[0].indexOf("(")+1,
                            metes[0].indexOf(")")).split("/");
                    MeteorologicalBean mb = new MeteorologicalBean();
                    if(temps.length == 2){
                        mb.setMaxTemp(Float.parseFloat(temps[0].substring(
                                temps[0].indexOf("max:") + "max:".length(),
                                temps[0].indexOf("℃"))));
                        mb.setMinTemp(Float.parseFloat(temps[1].substring(
                                temps[1].indexOf("min:") + "min:".length(),
                                temps[1].indexOf("℃"))));
                    }
                    String hunidity = metes[1].substring(
                            metes[1].indexOf("(") + 1,
                            metes[1].indexOf(")"));
                    mb.setHumidity(Float.parseFloat(hunidity.substring(
                            0,hunidity.indexOf("%"))));
                    String WSP = metes[2].substring(
                            metes[2].indexOf("(") + 1,
                            metes[2].indexOf(")"));
                    mb.setWSP(Float.parseFloat(WSP.substring(
                            0,WSP.indexOf("m/s"))));
                    //此时key的单位是月，如果按天显示的话会太多密集
                    context.write(
                            new Text(array[0].substring(0,
                                    array[0].lastIndexOf("-"))),mb);
                    context.getCounter(Counters.ROWS).increment(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
