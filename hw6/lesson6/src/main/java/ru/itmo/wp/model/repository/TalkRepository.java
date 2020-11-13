package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    public void save(Talk talk);
    public Talk find(long talkId);
    public List<Talk> findAll();
    public List<Talk> findByUserId(long userId);
}
