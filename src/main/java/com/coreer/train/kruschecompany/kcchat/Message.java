package com.coreer.train.kruschecompany.kcchat;

/**
 * Created by aieremenko on 1/1/16.
 */
public class Message {
    private final User author;
    private final String content;

    public Message(final User author, final String content) {
        this.author = author;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        if (author != null ? !author.equals(message.author) : message.author != null) return false;
        return !(content != null ? !content.equals(message.content) : message.content != null);

    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
