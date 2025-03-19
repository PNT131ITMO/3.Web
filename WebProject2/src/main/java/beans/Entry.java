package beans;

import java.io.Serializable;

public class Entry implements Serializable {
    private double xVal;
    private double yVal;
    private double rVal;
    private String executionTime;
    private String currentTime;
    private boolean hitResult;

    public Entry(double xVal, double yVal, double rVal, String currentTime, String executionTime, boolean hitResult) {
        this.xVal = xVal;
        this.yVal = yVal;
        this.rVal = rVal;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
        this.hitResult = hitResult;
    }

    public Entry() {
        this(0.0, 0.0, 0.0, "", "", false);
    }

    public double getxVal() {
        return xVal;
    }

    public void setXVal(double xVal) {
        this.xVal = xVal;
    }

    public double getyVal() {
        return yVal;
    }

    public void setYVal(double yVal) {
        this.yVal = yVal;
    }

    public double getrVal() {
        return rVal;
    }

    public void setRVal(double rVal) {
        this.rVal = rVal;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public boolean getHitResult() {
        return hitResult;
    }

    public void setHitResult(boolean hitResult) {
        this.hitResult = hitResult;
    }

}
