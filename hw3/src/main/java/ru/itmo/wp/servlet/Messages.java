package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Messages extends HttpServlet {
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    //private String encoding = "utf-8";
    private static class Message {
        String user;
        String text;

        Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private final List<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        response.setCharacterEncoding(UTF_8)
//        request.setCharacterEncoding(UTF_8);
//        response.setContentType("application/json");
        response.setContentType("application/json; charset=windows-1251");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        if (uri.endsWith("auth")) {
            String user = request.getParameter("user");
            if (user == null) {
                user = (String) session.getAttribute("user");
            } else {
                session.setAttribute("user", user);
            }
            if (user == null) user = "";
            String json = new Gson().toJson(user);
            response.getWriter().print(json);
            response.getWriter().flush();
        } else if (uri.endsWith("findAll")) {
            String json;
            synchronized (messages) {
                json = new Gson().toJson(messages);
            }
            response.getWriter().print(json);
            response.getWriter().flush();
        } else if (uri.endsWith("add")) {
            String user = (String) session.getAttribute("user");
            String text = request.getParameter("text");
            synchronized (messages) {
                messages.add(new Message(user, text));
            }
        }


    }

}




