package com.jd.analysis.producedata;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xudi1 on 2017/3/7.
 */
public class OutputData {
    private static String temp_data_location = "E:\\Java-Project\\weather-analysis\\input\\temp";
    private static String data_location = "E:\\Java-Project\\weather-analysis\\input\\data";

    public static void main(String[] args){
        OutputTemp(temp_data_location);
        RemoveRepetition(temp_data_location,data_location);
    }

    /**
     * 随机产生气象数据，写入temp文件
     * @param tempPath
     */
    public static void OutputTemp(String tempPath){
        try {
            File file = new File(tempPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file, true);
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < 365; i++){
                stringBuffer.append(timeString(i)+"    Temp(max:" +maxTemp() + "℃/min:" +
                        minTemp() + "℃);Humidity("+ Humidity() +"%);WSP("+WSP()+"m/s)").append("\r\n");
                outputStream.write(stringBuffer.toString().getBytes("utf-8"));
            }
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对temp文件中的数据去重，存入data
     * @param tempPath
     * @param path
     */
    public static void RemoveRepetition(String tempPath,String path){
        Set<String> values = new LinkedHashSet<String>();
        try{
            /*
            生成一个空文件
             */
            File file = new File(path);
            if( !file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file,true);
            StringBuffer stringBuffer = new StringBuffer();
            /*
            读取有重复的临时数据
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tempPath)));
            String value = "";
            while((value = reader.readLine()) != null){
                values.add(value);
            }

            for(Object string : values){
                System.out.println(string);
                string = string + "\n";
                outputStream.write(string.toString().getBytes("utf-8"));
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * 这里没用一个带参的函数生成数据是因为不同的属性在它应有的范围中有不同的密集分布范围，方便以后修改
     * @return
     */
    public static float maxTemp() {
        float Max = 30, Min = 1.0f;
        BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
       // System.out.println(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());// 保留2位小数并四舍五入
        return Float.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
    public static float minTemp() {
        float Max = 6, Min = -10f;
        BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
       // System.out.println(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());// 保留2位小数并四舍五入
        return Float.valueOf(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
    public static float Humidity() {
        float Max = 50, Min = 0f;
        BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
        // System.out.println(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());// 保留2位小数并四舍五入
        return Float.valueOf(db.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
    }
    public static float WSP() {
        float Max = 30, Min = 0f;
        BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
        // System.out.println(db.setScale(2, BigDecimal.ROUND_HALF_UP).toString());// 保留2位小数并四舍五入
        return Float.valueOf(db.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
    }
    /**
     * 生成2000-01-01   之后XX天的标准日期格式的字符串
     * @throws ParseException
     */
    public static String timeString(int num) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date  currdate = format.parse("2000-01-01");
        //System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        //System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

    public static String plusDay2(int num){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

}
