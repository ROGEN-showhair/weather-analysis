package com.jd.analysis;

/**
 * Created by xudi1 on 2017/3/13.
 */
public class Constants {
    public static String upload_location = "hdfs://192.168.217.130:9000/user/hadoop/upload"; //上传文件路径
    private static String local_location = "E:\\Java-Project\\weather-analysis\\input\\test\\download";//下载文件路径
    public static String reduce_result = "hdfs://192.168.217.130:9000/user/hadoop/weatherout/part-r-00000";//运算的结果
    public static String input_location = "hdfs://192.168.217.130:9000/user/hadoop/input/data";//job读入路径

}
