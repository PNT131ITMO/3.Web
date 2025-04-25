package weblab.webproject4.beans;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ShapeArea implements ShapeAreaMBean, Serializable {
    private double area;
    @Override
    public void updateShapeArea(double r) {
        area =  calcTriangleArea(r) + calcRectangleArea(r) + calcCircleArea(r);
    }

    @Override
    public double getArea() {
        return area;
    }

    private double calcTriangleArea(double r) {
        return 0.5 * (r / 2.0) * r;
    }

    private double calcRectangleArea(double r) {
        return r * (r / 2.0);
    }

    private double calcCircleArea(double r) {
        return 0.25 * Math.PI * Math.pow(r / 2.0, 2);
    }
}
