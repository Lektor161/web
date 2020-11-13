package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        User user = (User) request.getSession().getAttribute("user");
        Event event = new Event();
        event.setUserId(user.getId());
        event.setEventType(Event.EventType.LOGOUT);
        eventService.save(event);

        request.getSession().removeAttribute("user");

        request.getSession().setAttribute("message", "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
