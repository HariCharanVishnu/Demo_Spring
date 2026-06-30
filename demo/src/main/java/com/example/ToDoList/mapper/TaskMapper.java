package com.example.ToDoList.mapper;

import com.example.ToDoList.dto.TaskDto;
import com.example.ToDoList.dto.TaskCreateDto;
import com.example.ToDoList.dto.TaskUpdateDto;
import com.example.ToDoList.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    // Entity → DTO
    public static TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId()); // include id for display
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        return dto;
    }

    // CreateDto → Entity
    public static Task toTask(TaskCreateDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(false); // default new tasks as incomplete
        return task;
    }

    // UpdateDto → update existing Entity
    public static void updateTask(TaskUpdateDto dto, Task task) {
        if (dto.getTitle() != null) task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
    }

    // Convert List<Entity> → List<DTO>
    public static List<TaskDto> toDTOList(List<Task> entities) {
        return entities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }
}
