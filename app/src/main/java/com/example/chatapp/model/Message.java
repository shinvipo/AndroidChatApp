package com.example.chatapp.model;

public class Message {
    private String Sender, Receiver, content;

    public Message() {
    }

    public Message(String sender, String receiver, String content) {
        Sender = sender;
        Receiver = receiver;
        this.content = content;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
