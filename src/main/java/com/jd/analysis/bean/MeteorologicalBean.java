package com.jd.analysis.bean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class MeteorologicalBean implements Writable {

    private float MinTemp;
    private float MaxTemp;
    private float Humidity;
    private float WSP;

    /**
     * 不能省略，否则会报错：java.io.IOException: Unable to initialize any output collector
     */
    public MeteorologicalBean() {
        super();
    }

    public MeteorologicalBean(float minTemp, float maxTemp, float humidity, float WSP) {
        MinTemp = minTemp;
        MaxTemp = maxTemp;
        Humidity = humidity;
        this.WSP = WSP;
    }

    public float getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(float minTemp) {
        MinTemp = minTemp;
    }

    public float getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        MaxTemp = maxTemp;
    }

    public float getHumidity() {
        return Humidity;
    }

    public void setHumidity(float humidity) {
        Humidity = humidity;
    }

    public float getWSP() {
        return WSP;
    }

    public void setWSP(float WSP) {
        this.WSP = WSP;
    }

    @Override
    public String toString() {
        return "MeteorologicalBean{" +
                "MinTemp=" + MinTemp +
                ", MaxTemp=" + MaxTemp +
                ", Humidity=" + Humidity +
                ", WSP=" + WSP +
                '}';
    }

    /**
     * 序列化
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(MinTemp);
        dataOutput.writeFloat(MaxTemp);
        dataOutput.writeFloat(Humidity);
        dataOutput.writeFloat(WSP);
    }

    /**
     * 反序列化
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.MinTemp = dataInput.readFloat();
        this.MaxTemp = dataInput.readFloat();
        this.Humidity = dataInput.readFloat();
        this.WSP = dataInput.readFloat();

    }
}
