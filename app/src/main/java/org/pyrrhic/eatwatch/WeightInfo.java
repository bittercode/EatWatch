package org.pyrrhic.eatwatch;

/**
 * Created by jr.peck on 2/4/14.
 */
public class WeightInfo {
    private long id;
    private String wdate;
    private String weight;
    private String average;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        String all;
        all = wdate + " " + weight + " " + average;
        return all;
    }
}
