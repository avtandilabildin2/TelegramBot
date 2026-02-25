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

    public TelegramBot(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();

            if (text.equals("/start")) {
                // Приветственное сообщение
                sendMessage(chatId, "Привет! 👋 Я твой Telegram-бот.\nРад тебя видеть!");

                // Сохраняем пользователя в базу
                userRepository.save(
                        new UserEntity(chatId, username)
                );

                sendMessage( chatId,"Вы подписались на уведомления ✅");
            }
        }
    }

    // Метод для отправки уведомления
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
