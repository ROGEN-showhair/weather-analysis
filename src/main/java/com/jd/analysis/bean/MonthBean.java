package com.jd.analysis.bean;

/**
 * Created by xudi1 on 2017/3/8.
 */
public class MonthBean {
    private String Month;
    private float MaxTemp;
    private float MinTemp;
    private float Humidity;
    private float WSP;

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public float getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        MaxTemp = maxTemp;
    }

    public float getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(float minTemp) {
        MinTemp = minTemp;
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
        return "MonthBean{" +
                "Month='" + Month + '\'' +
                ", MaxTemp=" + MaxTemp +
                ", MinTemp=" + MinTemp +
                ", Humidity=" + Humidity +
                ", WSP=" + WSP +
                '}';
    }
}
