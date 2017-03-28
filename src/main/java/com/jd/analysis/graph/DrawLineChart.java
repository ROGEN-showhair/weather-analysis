package com.jd.analysis.graph;

import com.jd.analysis.bean.MonthBean;
import com.jd.analysis.data.ReadResult;
import org.jfree.chart.*;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by xudi1 on 2017/3/12.
 */
public class DrawLineChart {
    private static final String image_path = "E:\\Java-Project\\weather-analysis\\web\\image\\Result.jpg";
    public static void main(String[] args){
        DrawGraph();
    }

    public static void DrawGraph(){

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
        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);

        /*File fos_jpg = new File(image_path);
        ChartUtilities.saveChartAsJPEG(fos_jpg, mChart, // 统计图表对象
                700, // 宽
                500 // 高
        );*/

        mChartFrame.pack();
        mChartFrame.setVisible(true);

    }

    /**
     * 生成一个CategoryDataset对象
     * @param
     * @return
     */
    public static CategoryDataset CreateLine(List<MonthBean> list){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (MonthBean monthBean : list) {
            dataset.addValue(monthBean.getMaxTemp(),"MaxTemp",monthBean.getMonth());
            dataset.addValue(monthBean.getMinTemp(),"MinTemp",monthBean.getMonth());
            dataset.addValue(monthBean.getHumidity(),"Humidity",monthBean.getMonth());
            dataset.addValue(monthBean.getWSP(),"WSP",monthBean.getMonth());
        }
        return dataset;
    }


}
