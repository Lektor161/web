package ru.itmo.wp.model.domain;

import java.util.Date;

public class Event extends AbstractDomain {
    private long userId;
    private EventType eventType;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType.toString();
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public enum EventType {
        ENTER, LOGOUT;
    }
}
