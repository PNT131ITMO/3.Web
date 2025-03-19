package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/controller"})
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("clear") != null && request.getParameter("clear").equals("true")) {
            getServletContext().getRequestDispatcher("/clear").forward(request, response);
        } else if (request.getParameter("xVal") != null && request.getParameter("yVal") != null
                && request.getParameter("rVal") != null) {
            getServletContext().getRequestDispatcher("/check").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/badRequest.jsp").forward(request, response);
        }
    }

}
