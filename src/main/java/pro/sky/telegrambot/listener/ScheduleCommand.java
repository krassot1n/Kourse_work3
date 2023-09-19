package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ScheduleCommand implements Command{

    public static final Logger logger = LoggerFactory.getLogger(ScheduleCommand.class);

    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");
    private static final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");

    private final TelegramBot bot;
    private final NotificationRepository repository;

    public ScheduleCommand(TelegramBot bot, NotificationRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    @Override
    public void handle(Update update) {
        var chatId = update.message().chat().id();
        var matcher = PATTERN.matcher(update.message().text());
        if (matcher.matches()){
           var dateTime = parse(matcher.group(1));
           if (dateTime == null){
               bot.execute(new SendMessage(chatId, "Формат даты указан неверно!"));
               return;
           }
           var text = matcher.group(3);
            var saved = repository.save(new NotificationTask(text, chatId, dateTime));
            bot.execute(new SendMessage(chatId, "Уведомление создано!"));
            logger.info("Notification saved: {}", saved);

        }

    }

    @Override
    public boolean isSuitable(Update update) {
        return Optional.of(update).map(Update::message)
                .map(Message::text)
                .map(PATTERN::matcher)
                .map(Matcher::find)
                .orElse(false);

    }
    private LocalDateTime parse(String text){
        try{
            return LocalDateTime.parse(text, DATE_TIME_PATTERN);
        }catch (DateTimeException e){

        }
        return null;
    }

    @Override
    public String commandText() {
        return null;
    }
}
