package com.example.sky_telegram2.service;

import com.example.sky_telegram2.bot.TelegramBot;
import com.example.sky_telegram2.entity.NotificationTask;
import com.example.sky_telegram2.entity.UserEntity;
import com.example.sky_telegram2.repository.NotificationTaskRepository;
import com.example.sky_telegram2.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class NotificationTaskService {

    private final NotificationTaskRepository repository;
    private final TelegramBot telegramBot;
    private final UserRepository userRepository;

    private final Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})\\s+(.+)");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public NotificationTaskService(NotificationTaskRepository repository,
                                   TelegramBot telegramBot,
                                   UserRepository userRepository) {
        this.repository = repository;
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
    }

    public NotificationTask parseAndSave(String inputMessage) {

        Matcher matcher = pattern.matcher(inputMessage);

        if (matcher.matches()) {

            String dateTimeStr = matcher.group(1);
            String text = matcher.group(3);

            LocalDateTime notificationTime =
                    LocalDateTime.parse(dateTimeStr, formatter);

            NotificationTask task =
                    new NotificationTask(text, notificationTime);

            return repository.save(task);
        }

        throw new IllegalArgumentException("Неверный формат");
    }
    @Scheduled(cron = "0 0/1 * * * *")
    public void checkAndSendNotifications() {

        LocalDateTime now = LocalDateTime.now();

        List<NotificationTask> tasks =
                repository.findBySentFalseAndNotificationTimeBefore(now);

        List<UserEntity> users = userRepository.findAll();

        for (NotificationTask task : tasks) {

            for (UserEntity user : users) {
                telegramBot.sendMessage(user.getChatId(), task.getText());
            }

            task.setSent(true);
            repository.save(task);
        }
    }
}
