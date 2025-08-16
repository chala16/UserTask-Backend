package com.example.demo.repos;

import com.example.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Integer userId);
    List<Task> findByUserIdAndDueDate(Integer userId, Date dueDate);
}

