public class FastCGIRequest {
    private double x;
    private double y;
    private double r;

    public FastCGIRequest(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    @Override
    public String toString() {
        return "Request{" + "x=" + x + ", y=" + y + ", r=" + r + "}";
    }
}
