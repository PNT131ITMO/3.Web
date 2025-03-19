package choisoojong.servlets;

import jakarta.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/controller", "/controller2"})
public class ControllerServlet extends HttpServlet {
    // GET Request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("clear") != null && request.getParameter("clear").equals("true")) {
            getServletContext().getRequestDispatcher("/ClearServlet").forward(request, response);
        } else if (request.getParameter("xVal") != null && request.getParameter("yVal") != null
                && request.getParameter("rVal") != null) {
            getServletContext().getRequestDispatcher("/AreaCheckServlet").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
