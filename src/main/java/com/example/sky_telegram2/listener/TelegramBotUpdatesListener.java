package com.example.sky_telegram2.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {

            logger.info("Processing update: {}", update);

            // Проверяем, что сообщение существует
            if (update.message() != null && update.message().text() != null) {

                String text = update.message().text();

                // Проверяем команду /start
                if (text.equals("/start")) {

                    Long chatId = update.message().chat().id();

                    SendMessage message = new SendMessage(
                            chatId,
                            "Привет! 👋 Я твой Telegram-бот.\nРад тебя видеть!"
                    );

                    telegramBot.execute(message);
                }
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}

