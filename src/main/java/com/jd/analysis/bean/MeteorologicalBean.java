package com.jd.analysis.bean;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class MeteorologicalBean {

    private float MinTemp;
    private float MaxTemp;
    private float Humidity;
    private float WSP;

    public MeteorologicalBean() {
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
}
