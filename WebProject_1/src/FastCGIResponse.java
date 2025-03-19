import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FastCGIResponse {
    private double x;
    private double y;
    private double r;
    private String currTime;
    private float execTime;
    private boolean hitResult;

    public FastCGIResponse(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.currTime = fm.format(LocalDateTime.now());
        Instant startTime = Instant.now();
        this.hitResult = Checker.checkHit(x,y,r);
        Instant endTime = Instant.now();
        this.execTime = (Duration.between(startTime, endTime).toNanos());
    }

    public static FastCGIResponse createNewResponse(FastCGIRequest request) {
        return new FastCGIResponse(request.getX(), request.getY(), request.getR());
    }
}
