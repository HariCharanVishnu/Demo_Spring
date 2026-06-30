package com.example.ToDoList.service;

import com.example.ToDoList.entity.Task;
import com.example.ToDoList.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();

    }

    // Get task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Create new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Update existing task
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        });
    }

    // Delete task
    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }
}
