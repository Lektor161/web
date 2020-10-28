package ru.itmo.web.lesson4.model;

public class Post {
    private final Long id;
    private final Long user_id;
    private final String title;
    private final String text;

    public Post(Long id, Long user_id, String title, String text) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
