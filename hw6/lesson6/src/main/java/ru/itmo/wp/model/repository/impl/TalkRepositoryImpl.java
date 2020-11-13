package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class TalkRepositoryImpl extends BasicRepositoryImpl<Talk> implements TalkRepository {

    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public TalkRepositoryImpl() {
        super("Talk");
    }

    @Override
    public void save(Talk talk) {
        save(new HashMap<String, Object>(){{
            put("sourceUserId", talk.getSourceUserId());
            put("targetUserId", talk.getTargetUserId());
            put("text", talk.getText());
        }}, talk);
    }

    @Override
    public List<Talk> findByUserId(long userId) {
        return executeSQLQuery(
                "SELECT * FROM Talk WHERE sourceUserId=? or targetUserId=?",
                Arrays.asList(userId, userId),
                statement -> {
                    List<Talk> talks = new ArrayList<>();
                    try (ResultSet resultSet = statement.executeQuery()) {
                        Talk talk;
                        while ((talk = toModel(statement.getMetaData(), resultSet)) != null) {
                            talks.add(talk);
                        }
                    }
                    return talks;
                }
        );
    }

    protected Talk toModel(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }
}
