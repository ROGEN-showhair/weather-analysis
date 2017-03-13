package com.jd.analysis.hadooptestjob;

import com.jd.analysis.bean.MeteorologicalBean;
import com.jd.analysis.mapreduce.MeteorologicalMapper;
import org.apache.hadoop.io.Text;

import java.io.*;

/**
 * Created by xudi1 on 2017/3/13.
 */
public class TestMapAnalysis {
    private static String data_location = "E:\\Java-Project\\weather-analysis\\input\\data";
    private static String map_temp = "E:\\Java-Project\\weather-analysis\\input\\test\\maptemp";

    public static void main(String[] args){
        try{
            File file = new File(map_temp);
            if( !file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file,true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(data_location)));
            String value = "";
            while((value = reader.readLine()) != null){
                outputStream.write(Mapanalysis(value).toString().getBytes("utf-8"));
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String Mapanalysis(String value){
        String result = "";
        String[] array = value.toString().split("\t");
        //如果读入数据完整：Time  Temp{max:/min:};Humidity{};WSP{}
        if(array.length == 2){
            //把后面的值保存到 metes 中： Temp{max:/min:};Humidity{};WSP{}
            String[] metes = array[1].split(";");
            //System.out.println(metes[1]);
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
                    System.out.println(array[0].substring(0,
                            array[0].lastIndexOf("-")) + "\t" +mb.toString() );
                    result = array[0].substring(0, array[0].lastIndexOf("-")) + "\t" +mb.toString()+"\n";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
