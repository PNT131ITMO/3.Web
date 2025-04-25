package weblab.webproject4.beans;

import org.springframework.stereotype.Component;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PointStats extends NotificationBroadcasterSupport implements PointStatsMBean, Serializable {
    private final AtomicInteger totalPoints = new AtomicInteger();
    private final AtomicInteger totalMisses = new AtomicInteger();
    private long sequenceNumber = 1;

    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public int getTotalMisses() {
        return totalMisses.get();
    }

    @Override
    public void checkForOutOfBounds(double x, double y, double r) {
        if (x < -r - 1 || x > r + 1 || y < -r - 1 || y > r + 1) {
            Notification notification = new Notification(
                    "point.outOfCoordinatesPlane",
                    this,
                    sequenceNumber++,
                    System.currentTimeMillis(),
                    "The point (" + x + ", " + y + ") is out of coordinate plane for r=" + r
            );
            sendNotification(notification);
        }
    }

    public void updateStats(boolean hit, double x, double y, double r) {
        totalPoints.incrementAndGet();
        if (!hit) {
            totalMisses.incrementAndGet();
        }
        checkForOutOfBounds(x, y, r);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{"point.outOfCoordinatesPlane"};
        String name = Notification.class.getName();
        String description = "Point is out of the displayed area";
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(types, name, description)
        };
    }
}
