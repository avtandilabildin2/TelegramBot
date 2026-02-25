package com.example.sky_telegram2.controller;

import com.example.sky_telegram2.entity.NotificationTask;
import com.example.sky_telegram2.service.NotificationTaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationTaskController {

    private final NotificationTaskService service;

    public NotificationTaskController(NotificationTaskService service) {
        this.service = service;
    }

    // POST-запрос для добавления напоминания
    @PostMapping("/add")
    public NotificationTask addNotification(
            @RequestParam Long chatId,
            @RequestParam String message
    ) {
        return service.parseAndSave( message);
    }
}
