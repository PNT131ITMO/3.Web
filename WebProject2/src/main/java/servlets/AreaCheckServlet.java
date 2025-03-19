package servlets;

import beans.EntriesBean;
import beans.Entry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@WebServlet(name = "AreaCheckServlet", urlPatterns = "/check")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long beginTime = System.nanoTime();

        String xStr = request.getParameter("xVal");
        String yStr = request.getParameter("yVal");
        String rStr = request.getParameter("rVal");

        boolean validValue = validateCoordinate(xStr, yStr, rStr);

        if (validValue) {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            double r = Double.parseDouble(rStr);

            boolean isHit = checkHit(x, y, r);

            OffsetDateTime currentTimeObject = OffsetDateTime.now(ZoneOffset.UTC);
            String currentTime;

            try {
                currentTimeObject = currentTimeObject.minusMinutes(Long.parseLong(request.getParameter("timezone")));
                currentTime = currentTimeObject.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
            } catch (Exception e) {
                currentTime = "hh:mm:ss";
            }

            String executionTime = String.valueOf(System.nanoTime() - beginTime);

            EntriesBean entries = (EntriesBean) request.getSession().getAttribute("entries");
            if (entries == null) entries = new EntriesBean();

            entries.getEntries().add(new Entry(x, y, r, currentTime, executionTime, isHit));

            request.getSession().setAttribute("entries", entries);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/badRequest.jsp").forward(request, response);
        }


    }

    private boolean validateX(String xStr) {
        try {
            double x = Double.parseDouble(xStr);
            return x >= -5 && x <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateY(String yStr) {
        try {
            Double[] yValues = {-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0};
            double y = Double.parseDouble(yStr);
            return Arrays.asList(yValues).contains(y);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateR(String rStr) {
        try {
            double r = Double.parseDouble(rStr);
            return r >= 2 && r <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateCoordinate(String xStr, String yStr, String rStr) {
        return validateX(xStr) && validateY(yStr) && validateR(rStr);
    }

    private boolean checkRectangle(double x, double y, double r) {
        return x <= 0 && y >= 0 && x >= -r / 2 && y <= r;
    }

    private boolean checkTriangle(double x, double y, double r) {
        return x <= 0 && y <= 0 && y >= (-x - r);
    }

    private boolean checkCircle(double x, double y, double r) {
        return x >= 0 && y >= 0 && (x * x + y * y) <= (r * r / 4);
    }

    private boolean checkHit(double x, double y, double r) {
        return checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r);
    }
}
