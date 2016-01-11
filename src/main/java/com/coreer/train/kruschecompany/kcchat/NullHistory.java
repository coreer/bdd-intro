package com.coreer.train.kruschecompany.kcchat;

import java.util.Collections;
import java.util.List;

/**
 * Created by aieremenko on 1/9/16.
 */
public class NullHistory implements History {
    @Override
    public Chat getChat() {
        return null;
    }

    @Override
    public Message last() {
        return null;
    }

    @Override
    public History add(Message message) {
        return this;
    }

    @Override
    public List<Message> fromLastToFirst() {
        return Collections.emptyList();
    }
}
