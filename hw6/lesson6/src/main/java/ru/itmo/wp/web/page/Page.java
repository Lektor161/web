package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected final TalkService talkService = new TalkService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        setUser((User) request.getSession().getAttribute("user"), view);
        setMessage(view, (String) request.getSession().getAttribute("message"));
        request.getSession().removeAttribute("message");
    }

    private void setUser(User user, Map<String, Object> view) {
        if (user != null) {
            view.put("user", user);
        }
    }

    protected User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    protected void setMessage(Map<String, Object> view, String message) {
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
        }
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }
}
