package com.example.sky_telegram2.bot;

import com.example.sky_telegram2.entity.UserEntity;
import com.example.sky_telegram2.repository.UserRepository;
import com.example.sky_telegram2.service.NotificationTaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    private final UserRepository userRepository;
    private final NotificationTaskService notificationTaskService;

    public TelegramBot(UserRepository userRepository,
                       NotificationTaskService notificationTaskService) {
        this.userRepository = userRepository;
        this.notificationTaskService = notificationTaskService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();

        if (text.equals("/start")) {
            sendMessage(chatId, "Привет 👋\nОтправь сообщение в формате:\n01.01.2026 20:00 Сделать домашнюю работу");
            userRepository.save(new UserEntity(chatId, username));
            sendMessage(chatId, "Вы подписались на уведомления ✅");
            return;
        }

        try {
            notificationTaskService.parseAndSave(chatId, text);
            sendMessage(chatId, "✅ Уведомление успешно создано!");
        } catch (Exception e) {
            sendMessage(chatId, "❌ Неверный формат.\nИспользуй:\n01.01.2026 20:00 Сделать домашнюю работу");
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}