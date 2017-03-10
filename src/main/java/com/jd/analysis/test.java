package com.jd.analysis;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by xudi1 on 2017/3/7.
 */
public class test {
    public static void maisn(String[] args) throws ParseException {
       /* Date date = new Date();
        System.out.println(dateFormat(date));
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1 = dateFormat1.parse("2009-06-01");
        System.out.println(myDate1);


        //获得2010年9月13日22点36分01秒 的Date对象
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate2 = dateFormat2.parse("2010-09-13 22:36:01");
        System.out.println(myDate2);*/

            int max=20;
            int min=10;
            Random random = new Random();

            int s = random.nextInt(max)%(max-min+1) + min;
            System.out.println(s);

    }

    /**
     * 生成指定范围内包含两位小数的随机数
     * @param args
     */
    public static void main(String[] args) {
        String time = "2017-03-08";
        String s = time.substring(0, time.lastIndexOf("-"));
        System.out.println("After substring:  " + s);
    }

}
