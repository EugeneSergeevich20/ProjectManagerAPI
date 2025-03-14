package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.dto.CreateTaskRequest;
import com.manager.projectmanagerapi.dto.TaskDTO;
import com.manager.projectmanagerapi.dto.UpdateTaskRequest;
import com.manager.projectmanagerapi.entity.Project;
import com.manager.projectmanagerapi.entity.Tag;
import com.manager.projectmanagerapi.entity.Task;
import com.manager.projectmanagerapi.repository.ProjectRepository;
import com.manager.projectmanagerapi.repository.TagRepository;
import com.manager.projectmanagerapi.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    /**
     * Задачи проекта
     * @param projectId
     * @return
     */
    public List<TaskDTO> getTasksByProject(UUID projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Добааление новой задачи
     * @param request
     * @return
     */
    public TaskDTO createTask(CreateTaskRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Set<Tag> tags = getOrCreateTags(request.getTags());

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .project(project)
                .tags(tags)
                .build();
        return convertToDTO(taskRepository.save(task));
    }

    /**
     * Обновление задачи
     * @param taskId
     * @param request
     * @return
     */
    public TaskDTO updateTask(UUID taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getTags() != null) task.setTags(getOrCreateTags(request.getTags()));

        return convertToDTO(taskRepository.save(task));
    }

    /**
     * Удаление задачи
     * @param taskId
     */
    public void deleteTask(UUID taskId) {
        if (!taskRepository.existsById(taskId))
            throw new EntityNotFoundException("Task not found");
        taskRepository.deleteById(taskId);
    }

    /**
     * Возвращает существующие теги или создает новые, если они отсутствуют в базе данных.
     * Позволяет избежать дублирования тегов и гарантирует, что каждая задача может использовать уже существующий тег
     * @param tagNames
     * @return
     */
    private Set<Tag> getOrCreateTags(Set<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) return new HashSet<>();
        return tagNames.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(new Tag(null, name, new HashSet<>()))))
                .collect(Collectors.toSet());
    }

    /**
     * Конвертация в DTO
     * @param task
     * @return
     */
    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .projectId(task.getProject().getId())
                .tags(task.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .build();
    }
}
