package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;

public interface Command {

    void handle(Update update);

    boolean isSuitable(Update update);

    String commandText();
}
