package com.example.sky_telegram2.configuration;


import com.example.sky_telegram2.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String token;

//    @Bean
//    public TelegramBot telegramBot() {
//        return new TelegramBot("6915026805:AAGSyEmbkz-kGduQVDcz2lgmXDF-yK3SvPY");
//    }

}