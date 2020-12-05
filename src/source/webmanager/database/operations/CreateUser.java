package webmanager.database.operations;

import webmanager.database.abstractions.User;
import webmanager.security.Checker;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser implements DatabaseOperation<Void, User> {
    @Override
    public Void operate(Statement statement, User user) {
        if (Checker.isContainsWrong(user.getEmail()) || Checker.isContainsWrong(user.getPassword()) ||
                Checker.isContainsWrong(user.getUsername()))
            return null;

        try {
            statement.executeUpdate("INSERT INTO simpleweb.user_data(USERNAME, PASS, EMAIL) VALUES ('" + user.getUsername() +
                    "', '" + user.getPassword() + "', '" + user.getEmail() + "');");
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
