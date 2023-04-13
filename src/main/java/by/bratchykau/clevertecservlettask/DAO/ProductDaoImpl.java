package by.bratchykau.clevertecservlettask.DAO;

import by.bratchykau.clevertecservlettask.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private Connection connection;

    // Constructor
    public ProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private Product getProductFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int price = rs.getInt("price");
        int quantity = rs.getInt("quantity");
        return new Product(id, name, price, quantity);
    }

    @Override
    public List<Product> findAll(int offset, int limit) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id OFFSET ? LIMIT ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, offset);
        ps.setInt(2, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product product = getProductFromResultSet(rs);
            products.add(product);
        }
        return products;
    }

    @Override
    public Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getProductFromResultSet(rs);
        } else {
            return null;
        }
    }

    @Override
    public void create(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.executeUpdate();
    }

    @Override
    public void update(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.setInt(4, product.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
