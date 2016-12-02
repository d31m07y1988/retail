package ru.shop.repository;

import ru.shop.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramil on 02.12.2016.
 */
public class JDBCProductRepositoryImpl implements ProductRepository {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Product get(long id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            Product product = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("count")
                );
            }
            rs.close();
            ps.close();
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM products";

        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Product> productList = new ArrayList<>();
            Product product = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                product = new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("count"));
                productList.add(product);
            }
            rs.close();
            ps.close();
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByName(String productName) {
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, productName);
            List<Product> productList = new ArrayList<>();
            Product product = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                product = new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("count"));
                productList.add(product);
            }
            rs.close();
            ps.close();
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, count = ? WHERE id = ?";

        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getCount());
            ps.setLong(4, product.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
