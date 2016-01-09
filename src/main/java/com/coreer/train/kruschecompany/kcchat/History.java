package com.coreer.train.kruschecompany.kcchat;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aieremenko on 12/30/15.
 */
public class History {
    private final Chat chat;
    private Deque<Message> messages = new ArrayDeque<>();


    public History(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    public Message last() {
        return messages.peek();
    }

    public History add(Message message) {
        messages.push(message);
        return this;
    }

    public List<Message> fromLastToFirst() {
        return messages.stream()
                .map(
                        msg -> new Message(msg.getAuthor(), msg.getContent())
                )
                .collect(
                        Collectors.toList()
                );

    }
}
