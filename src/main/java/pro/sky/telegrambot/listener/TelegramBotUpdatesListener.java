package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    public static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final List<Command> commands;

    public TelegramBotUpdatesListener(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            logger.info("Handle update: {}", update);
            commands.stream().
                    filter(command -> command.isSuitable(update)).
                    forEach(command -> command.handle(update));
            }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
