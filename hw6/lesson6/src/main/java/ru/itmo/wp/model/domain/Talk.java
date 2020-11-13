package ru.itmo.wp.model.domain;

import java.util.Date;

public class Talk extends AbstractDomain {
    private Long sourceUserId;
    private Long targetUserId;
    private String text;

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
