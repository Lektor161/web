package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public UserRepositoryImpl() {
        super("User");
    }

    @Override
    public User findByLogin(String login) {
        return findByKey("login", login);
    }

    @Override
    public User findByEmail(String email) {
        return findByKey("email", email);
    }

    public User findByKeyAndPasswordSha(String key, String val, String passwordSha) {
        return executeSQLQuery(
                String.format("SELECT * FROM User WHERE %s=? AND passwordSha=?", key),
                Arrays.asList(val, passwordSha),
                statement -> {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        return toModel(statement.getMetaData(), resultSet);
                    }
                }
        );
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return findByKeyAndPasswordSha("login", login, passwordSha);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return findByKeyAndPasswordSha("email", email, passwordSha);
    }

    protected User toModel(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        save(new HashMap<String, Object>(){{
            put("login", user.getLogin());
            put("email", user.getEmail());
            put("passwordSha", passwordSha);
        }}, user);
    }

    @Override
    public int findCount() {
        return executeSQLQuery(
                "SELECT COUNT(*) FROM User",
                statement -> {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        resultSet.next();
                        return resultSet.getInt(1);
                    }
                }
        );
    }
}
