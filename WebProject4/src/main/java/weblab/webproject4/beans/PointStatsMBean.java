package weblab.webproject4.beans;

public interface PointStatsMBean {
    int getTotalPoints();
    int getTotalMisses();
    void checkForOutOfBounds(double x, double y, double r);
}