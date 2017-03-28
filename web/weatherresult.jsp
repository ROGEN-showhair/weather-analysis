<%@ page import="com.jd.analysis.bean.MonthBean" %><%--
  Created by IntelliJ IDEA.
  User: xudi1
  Date: 2017/3/14
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.jd.analysis.bean.MonthBean"%>
<%@ page import="org.jfree.chart.StandardChartTheme" %>
<%@ page import="java.awt.*" %>
<%@ page import="org.jfree.chart.ChartFactory" %>
<%@ page import="org.jfree.data.category.CategoryDataset" %>
<%@ page import="org.jfree.chart.JFreeChart" %>
<%@ page import="org.jfree.chart.plot.PlotOrientation" %>
<%@ page import="org.jfree.chart.plot.CategoryPlot" %>
<%@ page import="org.jfree.chart.ChartFrame" %>
<%@ page import="org.jfree.data.category.DefaultCategoryDataset" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@ page import="com.jd.analysis.data.ReadResult" %>
<%@ page import="org.jfree.chart.renderer.category.LineAndShapeRenderer" %>
<%@ page import="org.jfree.chart.labels.ItemLabelPosition" %>
<%@ page import="org.jfree.chart.labels.ItemLabelAnchor" %>
<%@ page import="org.jfree.ui.TextAnchor" %>
<%@ page import="org.jfree.chart.labels.StandardCategoryItemLabelGenerator" %>
<%!
    CategoryDataset CreateLine(List<MonthBean> list){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (MonthBean monthBean : list) {
            dataset.addValue(monthBean.getMaxTemp(),"MaxTemp",monthBean.getMonth());
            dataset.addValue(monthBean.getMinTemp(),"MinTemp",monthBean.getMonth());
            dataset.addValue(monthBean.getHumidity(),"Humidity",monthBean.getMonth());
            dataset.addValue(monthBean.getWSP(),"WSP",monthBean.getMonth());
        }
        return dataset;
    }

%>
<%
    List<MonthBean> monList = (List<MonthBean>) request.getAttribute("list");
    //out.println(monList.toString());
    Float avgMax = (Float) request.getAttribute("maxTemp");
    Float avgMin = (Float) request.getAttribute("minTemp");
    Float avgHum = (Float) request.getAttribute("humidity");
    Float avgWSP = (Float) request.getAttribute("WSP");
    StandardChartTheme mChartTheme = new StandardChartTheme("CN");
    mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
    mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
    mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 10));
    ChartFactory.setChartTheme(mChartTheme);

    List<MonthBean> list = ReadResult.Read();

    CategoryDataset mDataset = CreateLine(list);

    JFreeChart mChart = ChartFactory.createLineChart(
            "气象数据统计",
            "年份",
            "单位：摄氏度、百分比、米每秒",
            mDataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);
    CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
    mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
    mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
    mPlot.setOutlinePaint(Color.RED);//边界线

    // 数据渲染部分 主要是对折线做操作
    LineAndShapeRenderer renderer = (LineAndShapeRenderer) mPlot
            .getRenderer();
    renderer.setBaseItemLabelsVisible(true);
    renderer.setSeriesPaint(0, Color.black);    //设置折线的颜色
    renderer.setBaseShapesFilled(true);
    renderer.setBaseItemLabelsVisible(true);
    renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
    renderer
            .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
/*
*这里的StandardCategoryItemLabelGenerator()我想强调下：当时这个地*方被搅得头很晕，Standard**ItemLabelGenerator是通用的 因为我创建*的是CategoryPlot   所以很多设置都是Category相关
*而XYPlot  对应的则是 ： StandardXYItemLabelGenerator
*/
//对于编程人员  这种根据一种类型方法联想到其他类型相似方法的思
//想是必须有的吧！目前只能慢慢培养了。。
    renderer.setBaseItemLabelFont(new Font("Dialog", 2, 10));  //设置提示折点数据形状
    mPlot.setRenderer(renderer);

   // ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
    //mChartFrame.pack();
    //mChartFrame.setVisible(true);
    //保存图片 返回图片文件名
    String fileName = ServletUtilities.saveChartAsPNG(mChart,800,600,session);
    //ServletUtilities是面向web开发的工具类，返回一个字符串文件名,文件名自动生成，生成好的图片会自动放在服务器（tomcat）的临时文件下（temp）

    String url = request.getContextPath() + "/DisplayChart?filename=" + fileName;
    //根据文件名去临时目录下寻找该图片，这里的/DisplayChart路径要与配置文件里用户自定义的<url-pattern>一致
%>
<html>
<head>
    <title>Weather Result</title>
</head>
<body>
<%
    MonthBean monthBean = (MonthBean) request.getAttribute("bean");
    //out.println(monthBean.toString());
    out.println("全年气象数据平均值："+"<br>");
    out.println("最高温度："+avgMax+"℃<br>");
    out.println("最低温度："+avgMin+"℃<br>");
    out.println("湿    度："+avgHum+"%<br>");
    out.println("风    速："+avgWSP+"m/s<br>");

%>
<tr>
    <td >
        <img src="<%= url %>" width="800" height="600">
    </td>
</tr>
</body>
</html>
