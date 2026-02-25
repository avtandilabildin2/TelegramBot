package com.example.sky_telegram2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @Column(name="chat_id")
    private Long chatId;

    @Column(name = "username")
    private String username;

    public UserEntity() {}

    public UserEntity(Long chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getUsername() {
        return username;
    }
}
