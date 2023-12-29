package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StartCommand implements Command{

    private final TelegramBot bot;

    public StartCommand(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(Update update) {

        var chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId,"Привет, это Krassotin_bot"));
    }

    @Override
    public boolean isSuitable(Update update) {
        Optional.of(update)
                .map(Update::message)
                .map(Message::text)
                .map(text -> text.equals("/start"))
                .orElse(false);
    return false;
    }

    @Override
    public String commandText() {
        return "/start";
    }
}
