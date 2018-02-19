package ru.mininn.localchat.chat.model;

import java.util.Date;

public class Message {
    private int id;
    private User author;
    private String message;
    private long date;

    public Message(int id, User author, String message, long date) {
        this.id = id;
        this.author = author;
        this.message = message;
        this.date = date;
    }

    public Message(User author, String message, Date date) {
        this.author = author;
        this.message = message;
        this.date = date.getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return date;
    }

    public Date getDate() {
        return new Date(date);
    }

    public void setDate(int timestamp) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
