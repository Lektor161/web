package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser(request) == null) {
            request.getSession().setAttribute("message", "login to view talks page");
            throw new RedirectException("/index");
        }
        view.put("users", userService.findAll());
    }

    @Override
    protected void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("talks", talkService.findByUserId(getUser(request).getId()));
        super.after(request, view);
    }

    protected void send(HttpServletRequest request, Map<String, Object> view) {
        String message = request.getParameter("message");
        Long targetUserId = Long.parseLong(request.getParameter("target-user-id"));
        try {
            talkService.validateMessage(message, targetUserId);
            talkService.save(new Talk() {{
                setText(message);
                setTargetUserId(targetUserId);
                setSourceUserId(getUser(request).getId());
            }});
        } catch (ValidationException e) {
            setMessage(view, e.getMessage());
        }
    }
}
