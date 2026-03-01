package com.example.sky_telegram2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "notification_time", nullable = false)
    private LocalDateTime notificationTime;

    @Column(name = "sent", nullable = false)
    private boolean sent = false;

    public NotificationTask() {}

    public NotificationTask(Long chatId, String text, LocalDateTime notificationTime) {
        this.chatId = chatId;
        this.text = text;
        this.notificationTime = notificationTime;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getNotificationTime() { return notificationTime; }
    public void setNotificationTime(LocalDateTime notificationTime) { this.notificationTime = notificationTime; }

    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }
}