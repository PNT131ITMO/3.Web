package weblab.webproject3;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Entry implements Serializable {
    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private int id;

    private Double xVal;
    private Double yVal;
    private Integer rVal;
    private String hitResult;

    public Entry() {
    }

    private boolean checkTriangle() {
        return xVal <= 0 && yVal <= 0 && yVal >= (-xVal - (double) rVal);
    }

    private boolean checkRectangle() {
        return xVal >= 0 && xVal <= rVal / 2 && yVal >= (double) -rVal && yVal <= 0;
    }

    private boolean checkCircle() {
        return xVal <= 0 && yVal >= 0 && xVal * xVal + yVal * yVal <= (double) rVal * rVal / 4;
    }

    public void checkHit() {
        hitResult = checkCircle() || checkRectangle() || checkTriangle() ? "hit" : "miss";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getxVal() {
        return xVal;
    }

    public void setxVal(Double xVal) {
        this.xVal = xVal;
    }

    public Double getyVal() {
        return yVal;
    }

    public void setyVal(Double yVal) {
        this.yVal = yVal;
    }

    public Integer getrVal() {
        return rVal;
    }

    public void setrVal(Integer rVal) {
        this.rVal = rVal;
    }

    public String getHitResult() {
        return hitResult;
    }

    public void setHitResult(String hitResult) {
        this.hitResult = hitResult;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "xValue=" + xVal +
                ", yValue=" + yVal +
                ", rValue=" + rVal +
                ", hitResult=" + hitResult +
                '}';
    }

    @Override
    public int hashCode() {
        return xVal.hashCode() + yVal.hashCode() +
                rVal.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Entry) {
            Entry entryObj = (Entry) obj;
            return xVal.equals(entryObj.getxVal()) &&
                    yVal.equals(entryObj.getyVal()) &&
                    rVal.equals(entryObj.getrVal());
        }
        return false;
    }
}
