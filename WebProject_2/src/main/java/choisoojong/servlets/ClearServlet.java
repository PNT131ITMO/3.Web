package choisoojong.servlets;

import choisoojong.beans.EntriesBean;
import jakarta.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClearServlet", urlPatterns = {"/clear"})
public class ClearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy EntriesBean từ session
        EntriesBean entries = (EntriesBean) request.getSession().getAttribute("entries");

        // Nếu chưa có, khởi tạo mới
        if (entries == null) entries = new EntriesBean();

        // Xóa tất cả các entries trong bean
        entries.getEntries().clear();

        // Cập nhật lại entries vào session
        request.getSession().setAttribute("entries", entries);

        // Chuyển tiếp về trang index.jsp
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
