package org.example.repository;

import org.example.entity.Product;
import org.example.entity.User;
import org.example.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductRepository extends CrudRepository<Product> {

    public ProductRepository(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    public HashMap<Product, User> findWithReferences() {
        HashMap<Product, User> map = new HashMap<>();
        String sql = """
                SELECT product.id,
                       product.name,
                       product.price,
                       product.category,
                       u.id,
                       u.name,
                       u.surname,
                       u.date_of_birth
                FROM product
                         LEFT JOIN "user" u
                                   ON product.user_id = u.id""";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                map.put(Product.builder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .price(resultSet.getBigDecimal("price"))
                                .category(resultSet.getString("category"))
                                .build(),
                        User.builder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .surname(resultSet.getString("surname"))
                                .dateOfBirth(resultSet.getDate("date_of_birth"))
                                .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                products.add(Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .category(resultSet.getString("category"))
                        .userId(resultSet.getLong("user_id"))
                        .build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Product findById(long id) {
        Product product = null;
        String sql = "SELECT * FROM \"user\" WHERE id = ?";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .category(resultSet.getString("category"))
                        .userId(resultSet.getLong("user_id"))
                        .build();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public void saveOrUpdate(Product product) {
        if (product.getId() == 0) {

            String sql = "INSERT INTO product(name, price, category, user_id) VALUES(?, ?, ?, ?)";

            try (PreparedStatement statement = connectionManager.getConnection()
                    .prepareStatement(sql)) {

                statement.setString(1, product.getName());
                statement.setBigDecimal(2, product.getPrice());
                statement.setString(3, product.getCategory());
                statement.setLong(4, product.getUserId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {

            String sql = "UPDATE product SET name = ?, price = ?, category = ?, user_id = ? " +
                    "WHERE id = ?";

            try (PreparedStatement statement = connectionManager.getConnection()
                    .prepareStatement(sql)) {

                statement.setString(1, product.getName());
                statement.setBigDecimal(2, product.getPrice());
                statement.setString(3, product.getCategory());
                statement.setLong(4, product.getUserId());
                statement.setLong(5, product.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM product WHERE id = ?";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
