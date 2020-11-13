package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    TalkRepository talkRepository = new TalkRepositoryImpl();

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public List<Talk> findByUserId(long userId) {
        return  talkRepository.findByUserId(userId);
    }

    public void validateMessage(String message, Long targetUser) throws ValidationException {
        if (Strings.isNullOrEmpty(message)) {
            throw new ValidationException("message is required");
        }

        if (message.length() > 200) {
            throw new ValidationException("Message can't be longer than 200 characters");
        }

        if (new UserService().find(targetUser) == null) {
            throw new ValidationException("User you're trying to send message doesn't exist");
        }
    }
}
