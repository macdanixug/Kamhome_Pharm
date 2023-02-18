package com.example.kamhomepharmacy;

public class Message {
    private String username;
    private String message;
    private String dateTime;

    public Message() {}

    public Message(String username, String message, String dateTime) {
        this.username = username;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
