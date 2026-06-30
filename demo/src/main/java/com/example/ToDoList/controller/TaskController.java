package com.example.ToDoList.controller;

import com.example.ToDoList.dto.TaskCreateDto;
import com.example.ToDoList.dto.TaskUpdateDto;
import com.example.ToDoList.dto.TaskDto;
import com.example.ToDoList.entity.Task;
import com.example.ToDoList.exception.TaskNotFoundException;
import com.example.ToDoList.mapper.TaskMapper;
import com.example.ToDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // --- Home Page ---
    @GetMapping({"", "/"})
    public String home(Model model) {
        model.addAttribute("task", new TaskCreateDto()); // bind to DTO
        List<TaskDto> tasks = taskService.getAllTasks()
                .stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "home";
    }

    // --- List Page ---
    @GetMapping("/list")
    public String listTasks(Model model) {
        List<TaskDto> tasks = taskService.getAllTasks()
                .stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "tasks-list";
    }

    // --- Add Page ---
    @GetMapping("/add")
    public String addTaskPage(Model model) {
        model.addAttribute("task", new TaskCreateDto());
        return "task-add";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("task") TaskCreateDto taskDTO,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "task-add"; // redisplay form with validation errors
        }
        Task entity = TaskMapper.toTask(taskDTO); // ✅ maps CreateDto → Entity
        taskService.createTask(entity);
        return "redirect:/tasks/list";
    }

    // --- Edit Page ---
    @GetMapping("/edit/{id}")
    public String editTaskPage(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
        model.addAttribute("task", TaskMapper.toDto(task)); // DTO for form binding
        return "task-edit";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id,
                           @Valid @ModelAttribute("task") TaskUpdateDto taskDTO,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "task-edit"; // redisplay form with validation errors
        }
        Task entity = taskService.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
        TaskMapper.updateTask(taskDTO, entity); // ✅ applies UpdateDto → Entity
        taskService.updateTask(id, entity);
        return "redirect:/tasks/list";
    }

    // --- Delete Page ---
    @GetMapping("/delete/{id}")
    public String deleteTaskPage(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
        model.addAttribute("task", TaskMapper.toDto(task)); // DTO for confirmation page
        return "task-delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/list";
    }
}
