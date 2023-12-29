package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
public class Notification {
    public static final Logger logger = LoggerFactory.getLogger(Notification.class);
    private final TelegramBot bot;
    private final NotificationRepository repository;

    public Notification(TelegramBot bot, NotificationRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES,fixedDelay = 1)
    public void notifyTask(){
        repository.findByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)).forEach(notificationTask -> {
            bot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getTaskText()));
            logger.info("Notification sent: {}", notificationTask);
            repository.delete(notificationTask);
        });

    }
}
