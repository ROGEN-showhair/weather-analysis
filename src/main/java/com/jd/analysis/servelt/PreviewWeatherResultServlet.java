package com.jd.analysis.servelt;

import com.jd.analysis.bean.MonthBean;
import com.jd.analysis.bean.UserBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 这个类是处理用户上传上来的数据的，返回的是每个月的平均数据
 * Created by xudi1 on 2017/3/8.
 */
public class PreviewWeatherResultServlet extends HttpServlet {
    private static final long serialVersionUID = 6509648175195655953L;
    private static final Configuration conf = new Configuration();
    public PreviewWeatherResultServlet(){

    }

    /**
     * 处理用户提交的浏览云计算天气数据的结果，服务端读取mapreduce计算的生成的
     * 结果文件，然后把信息返回给客户端
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        UserBean ub = (UserBean)request.getSession().getAttribute("user");
        if(ub == null){
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }else {
            //开始读取结果文件
            FileSystem fs = FileSystem.get(conf);
            Path success = new Path(
                    "/tomcat/experiment/weathercloud/results/SUCCESS");
            if(fs.exists(success)){
                request.setAttribute("result",null);
                request.getRequestDispatcher("weathercloud.jsp").forward(request,response);
            }else {
                request.setAttribute("result","");
                Path reduceResult = new Path(
                        "/tomcat/experiment/weathercloud/results/part-r-00000");
                if(fs.exists(reduceResult)){
                    FSDataInputStream fsdis = fs.open(reduceResult);
                    BufferedReader br = new BufferedReader(
                      new InputStreamReader(fsdis,"UTF-8"));
                    String line = null;
                    int count = 0;
                    float maxTempTotal = 0.0f;
                    float minTempTotal = 0.0f;
                    float humidityTotal = 0.0f;
                    float WSPTotal = 0.0f;
                    //2012-01
                    //AVG { Temp ( max: asdfasfd^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                    //开始汇总读取到的每一行数据文件，主要的操作是把所有结果汇总，然后返回给客户端
                    while((line = br.readLine()) != null){
                        //把每行数据按Tab读取到array中
                        String[] array = line.split("\t");
                        //如果数据完整。。
                        if(array.length == 2){
                            MonthBean monBean = new MonthBean();
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
                                        maxTempTotal += monBean.getMaxTemp();
                                        monBean.setMinTemp(Float.parseFloat(temps[1].substring(
                                                temps[1].indexOf("min:") + "min:".length(),
                                                temps[1].indexOf("℃"))));
                                        minTempTotal += monBean.getMinTemp();
                                    }
                                    String humidity = metes[1].substring(
                                            metes[1].indexOf("(") + 1,
                                            metes[1].indexOf(")"));
                                    monBean.setHumidity(Float.parseFloat(humidity.substring(0,
                                            humidity.indexOf("%"))));
                                    humidityTotal += monBean.getHumidity();
                                    String WSP = metes[2].substring(
                                            metes[2].indexOf("(") + 1,
                                            metes[2].indexOf(")"));
                                    monBean.setWSP(Float.parseFloat(WSP.substring(0,
                                            WSP.indexOf("m/s"))));
                                    WSPTotal += monBean.getWSP();
                                    count++;
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            System.out.println(array[0].split("-")[1]);   //显示的是几月
                            request.setAttribute(array[0].split("-")[1],monBean);
                        }
                    }
                    //读取文件结果完成
                    request.setAttribute("maxTemp",maxTempTotal/count);
                    request.setAttribute("minTemp",minTempTotal/count);
                    request.setAttribute("humidity",humidityTotal/count);
                    request.setAttribute("WSP",WSPTotal/count);
                    request.getRequestDispatcher("/weatherresult.jsp").forward(request,response);
                }else {
                    request.setAttribute("result",null);
                }
            }

        }
    }
}
