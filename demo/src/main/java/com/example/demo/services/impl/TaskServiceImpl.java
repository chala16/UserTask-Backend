package com.example.demo.services.impl;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repos.TaskRepository;
import com.example.demo.repos.UserRepository;
import com.example.demo.services.interfaces.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findByUserId(getLoggedInUser().getId());
    }

    @Override
    public Task createTask(Task task) {
        task.setUser(getLoggedInUser());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User currentUser = getLoggedInUser();
        if (!existingTask.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not allowed to update this task");
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User currentUser = getLoggedInUser();
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not allowed to delete this task");
        }

        taskRepository.delete(task);
    }

    @Override
    public List<Task> getTodayTasks() {
        return taskRepository.findByUserIdAndDueDate(getLoggedInUser().getId(), new Date());
    }
}