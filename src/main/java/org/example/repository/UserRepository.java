package org.example.repository;

import org.example.entity.User;
import org.example.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserRepository extends CrudRepository<User> {

    public UserRepository(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    public List<User> findByName(String name) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" WHERE \"user\".name = ?";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .dateOfBirth(resultSet.getDate("date_of_birth"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\"";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .dateOfBirth(resultSet.getDate("date_of_birth"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public User findById(long id) {
        User user = null;
        String sql = "SELECT * FROM \"user\" WHERE id = ?";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .dateOfBirth(resultSet.getDate("date_of_birth"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public void saveOrUpdate(User user) {

        if (user.getId() == 0) {

            String sql = "INSERT INTO \"user\"(name, surname, date_of_birth) VALUES(?, ?, ?)";

            try (PreparedStatement statement = connectionManager.getConnection()
                    .prepareStatement(sql)) {

                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setDate(3, user.getDateOfBirth());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {

            String sql = "UPDATE \"user\" SET name = ?, surname = ?, date_of_birth = ? " +
                    "WHERE id = ?";

            try (PreparedStatement statement = connectionManager.getConnection()
                    .prepareStatement(sql)) {

                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setDate(3, user.getDateOfBirth());
                statement.setLong(4, user.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void delete(long id) {
        String deleteUserSql = "DELETE FROM \"user\" WHERE id = ?";
        String deleteProductSql = "DELETE FROM product WHERE product.user_id = ?";
        try (PreparedStatement userStatement = connectionManager.getConnection()
                .prepareStatement(deleteUserSql);
             PreparedStatement productStatement = connectionManager.getConnection()
                     .prepareStatement(deleteProductSql)) {

            productStatement.setLong(1, id);
            productStatement.executeUpdate();

            userStatement.setLong(1, id);
            userStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void safeDelete(long id) {
        String sql = "DELETE FROM \"user\" WHERE id = ?";

        try (PreparedStatement statement = connectionManager.getConnection()
                .prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
