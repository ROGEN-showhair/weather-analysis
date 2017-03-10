package com.jd.analysis.mapreduce;

import com.jd.analysis.bean.MeteorologicalBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class MeteorologicalReducer extends Reducer<Text,MeteorologicalBean,Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<MeteorologicalBean> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        float maxTempTotal = 0.0f;
        float minTempTotal = 0.0f;
        float humidityTotal = 0.0f;
        float WSPTotal = 0.0f;
        for (MeteorologicalBean mb : values){
            count++;
            maxTempTotal += mb.getMaxTemp();
            minTempTotal += mb.getMinTemp();
            humidityTotal += mb.getHumidity();
            WSPTotal += mb.getWSP();
        }
        context.write(key,new Text("AVG{Temp(max:" + maxTempTotal/count
                + "℃/min:" + minTempTotal/count + "℃);Humidity("
                + humidityTotal/count + "%);WSP(" + WSPTotal/count
                + "m/s)}"));
    }
}
