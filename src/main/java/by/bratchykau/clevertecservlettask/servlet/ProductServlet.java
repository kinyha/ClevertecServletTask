package by.bratchykau.clevertecservlettask.servlet;

import by.bratchykau.clevertecservlettask.DAO.ProductDao;
import by.bratchykau.clevertecservlettask.DAO.ProductDaoImpl;
import by.bratchykau.clevertecservlettask.DAO.DatabaseInitializer;
import by.bratchykau.clevertecservlettask.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/receipt_db";
            String user = "postgres";
            String password = "postgres";
            Connection connection = DriverManager.getConnection(url, user, password);
            DatabaseInitializer.init(connection);
            productDao = new ProductDaoImpl(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // retrieve query parameters for pagination
            int page = 1;
            int limit = 20;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("limit") != null) {
                limit = Integer.parseInt(request.getParameter("limit"));
            }
            int offset = (page - 1) * limit;
            List<Product> products = productDao.findAll(offset, limit);
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), products);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(request.getInputStream(), Product.class);
            productDao.create(product);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(request.getInputStream(), Product.class);
            productDao.update(product);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            productDao.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
