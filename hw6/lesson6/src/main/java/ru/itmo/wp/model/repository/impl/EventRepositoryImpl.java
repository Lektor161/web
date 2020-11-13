package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {

    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public EventRepositoryImpl() {
        super("Event");
    }

    @Override
    public void save(Event event) {
        save(new HashMap<String, Object>(){{
            put("userId", event.getUserId());
            put("type", event.getEventType());
        }}, event);
    }

    @Override
    protected Event toModel(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setEventType(resultSet.getString(i).equals("ENTER") ? Event.EventType.ENTER : Event.EventType.LOGOUT);
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }
}
