package com.coreer.train.kruschecompany.kcchat;

import java.util.List;

/**
 * Created by aieremenko on 1/9/16.
 */
public interface History {

    Chat getChat();

    Message last();

    History add(Message message);

    List<Message> fromLastToFirst();

}
