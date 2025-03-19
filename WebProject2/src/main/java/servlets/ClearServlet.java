package servlets;

import beans.EntriesBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ClearServlet", urlPatterns = "/clear")
public class ClearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntriesBean entries = (EntriesBean) request.getSession().getAttribute("entries");

        if (entries == null) entries = new EntriesBean();

        entries.getEntries().clear();

        request.getSession().setAttribute("entries", entries);

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
