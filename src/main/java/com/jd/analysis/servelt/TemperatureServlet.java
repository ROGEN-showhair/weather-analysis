package com.jd.analysis.servelt;

/**
 * Created by xudi1 on 2017/3/12.
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.Font;


public class TemperatureServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TemperatureServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doResponse(request,response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doResponse(request,response);
    }

    private void doResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _time = request.getParameter("time");
        String _data = request.getParameter("data");
        System.out.println("time:" + _time);
        System.out.println("data:" + _data);


        TimeSeries timeSeries = new TimeSeries("阿蜜果blog访问量统计", Month.class);
        //时间曲线数据集合
        TimeSeriesCollection lineDataset = new TimeSeriesCollection();

        //构造数据集合
        timeSeries.add(new Month(1, 2007), 11200);
        timeSeries.add(new Month(2, 2007), 9000);
        timeSeries.add(new Month(3, 2007), 6200);
        timeSeries.add(new Month(4, 2007), 8200);
        timeSeries.add(new Month(5, 2007), 8200);
        timeSeries.add(new Month(6, 2007), 12200);
        timeSeries.add(new Month(7, 2007), 13200);
        timeSeries.add(new Month(8, 2007), 8300);
        timeSeries.add(new Month(9, 2007), 12400);
        timeSeries.add(new Month(10, 2007), 12500);
        timeSeries.add(new Month(11, 2007), 13600);
        timeSeries.add(new Month(12, 2007), 2500);

        lineDataset.addSeries(timeSeries);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间线", "月份", "访问量", lineDataset, true, true, true);

        //设置子标题
        TextTitle subtitle = new TextTitle("2007年度", new Font("黑体", Font.BOLD, 12));
        chart.addSubtitle(subtitle);
        //设置主标题
        chart.setTitle(new TextTitle("阿蜜果blog访问量统计", new Font("隶书", Font.ITALIC, 15)));
        chart.setAntiAlias(true);


        /*字体设置（避免中文乱码）*/
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis valueAxis = plot.getDomainAxis();
        // 设置x轴上面的字体
        valueAxis.setTickLabelFont(new Font("隶书", Font.ITALIC, 12));
        // 设置X轴的标题文字
        valueAxis.setLabelFont(new Font("隶书", Font.ITALIC, 12));
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        // 设置y轴上的字体
        numberAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 13));
        // 设置y轴上的标题字体
        numberAxis.setLabelFont(new Font("宋体", Font.PLAIN, 13));
        // 设置底部的字体
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));


        //将图表以数据流的方式返回给客户端
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,500, 300);
    }
}