package com.example.sky_telegram2.repository;

import com.example.sky_telegram2.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    List<NotificationTask> findBySentFalseAndNotificationTimeBefore(LocalDateTime time);
}