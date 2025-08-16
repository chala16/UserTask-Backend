package com.example.demo.services.interfaces;

import com.example.demo.models.Task;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task createTask(Task task);
    Task updateTask(Long id, Task updatedTask);
    void deleteTask(Long id);
    List<Task> getTodayTasks();
}