package org.example;

import org.example.repository.UserRepository;
import org.example.util.ConnectionManager;

import java.sql.SQLException;

public class Main {

    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";

    private final static String USER = "postgres";

    private final static String PASSWORD = "1234";

    public static void main(String[] args) {
        try (ConnectionManager manager = new ConnectionManager(URL, USER, PASSWORD)) {
            UserRepository repository = new UserRepository(manager);

            repository.delete(4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}