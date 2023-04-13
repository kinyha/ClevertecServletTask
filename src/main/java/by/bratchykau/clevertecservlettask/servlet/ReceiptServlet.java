package by.bratchykau.clevertecservlettask.servlet;

import by.bratchykau.clevertecservlettask.DAO.ProductDaoImpl;
import by.bratchykau.clevertecservlettask.DAO.DatabaseInitializer;
import by.bratchykau.clevertecservlettask.model.DiscountCard;
import by.bratchykau.clevertecservlettask.model.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/receipts")
public class ReceiptServlet extends HttpServlet {
    private ProductDaoImpl productDAO;

    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("org.postgresql.Driver");
            // create a connection to the database
            String url = "jdbc:postgresql://localhost:5432/receipt_db";
            String user = "postgres";
            String password = "postgres";
            Connection connection = DriverManager.getConnection(url, user, password);
            DatabaseInitializer.init(connection);
            System.out.println("Initialization of database completed");

            productDAO = new ProductDaoImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        DiscountCard discountCard = null;
        int totalAmount = 0;

        // retrieve the product ids and quantity from the request parameters
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        // validate the product ids and quantity
        for (int i = 0; i < productIds.length; i++) {
            int productId = Integer.parseInt(productIds[i]);
            int quantity = Integer.parseInt(quantities[i]);
            Product product = null;
            try {
                product = productDAO.findById(productId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (product == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product id: " + productId);
                return;
            }

            products.add(product);
            totalAmount += product.getPrice() * quantity;
        }

        // retrieve the discount card number
        String discountCardNumber = request.getParameter("discountCardNumber");

        if (discountCardNumber != null && !discountCardNumber.isEmpty()) {
            discountCard = new DiscountCard(discountCardNumber, 10);
            totalAmount -= totalAmount * discountCard.getDiscountPercentage() / 100;
        }

        // generate PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"receipt.pdf\"");

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("Receipt"));
            document.add(new Paragraph("--------------------"));
            document.add(new Paragraph("Products:"));

            for (Product product : products) {
                document.add(new Paragraph("- " + product.getName() + ": $" + product.getPrice()));
            }

            if (discountCard != null) {
                document.add(new Paragraph("Discount Card:"));
                document.add(new Paragraph("- Number: " + discountCard.getNumber()));
                document.add(new Paragraph("- Discount Percentage: " + discountCard.getDiscountPercentage() + "%"));
            }

            document.add(new Paragraph("--------------------"));
            document.add(new Paragraph("Total Amount: $" + totalAmount));
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
