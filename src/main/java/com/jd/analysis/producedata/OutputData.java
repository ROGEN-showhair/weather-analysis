package com.jd.analysis.producedata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xudi1 on 2017/3/7.
 */
public class OutputData {
    private static String data_location = "E:\\Java-Project\\weather-analysis\\input\\data";

    /*public static void main(String[] args) throws ParseException {
        for (int i = 0; i < 100; i++) {
            System.out.println(timeString(i) + "    Temp{max:" +maxTemp() + "℃/min:" + minTemp() + "℃};Humidity{"+ Hunidity() +"}");
        }
        //System.out.println(timeString(100));
    }*/

    /**
     * 用Map实现数据去重
     * @param args
     */
    public static void main(String[] args){
        try {
            File file = new File(data_location);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file, true);
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < 100; i++){
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
