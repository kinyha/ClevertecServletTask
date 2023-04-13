package by.bratchykau.clevertecservlettask.DAO;

import by.bratchykau.clevertecservlettask.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    public List<Product> findAll(int offset, int limit) throws SQLException;
    public Product findById(int id) throws SQLException;
    public void create(Product product) throws SQLException;
    public void update(Product product) throws SQLException;
    public void delete(int id) throws SQLException;
}
