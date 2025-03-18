package com.manager.projectmanagerapi.controller;

import com.manager.projectmanagerapi.dto.CreateTaskRequest;
import com.manager.projectmanagerapi.dto.TaskDTO;
import com.manager.projectmanagerapi.dto.UpdateTaskRequest;
import com.manager.projectmanagerapi.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskRequest request) {
        TaskDTO createTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTask);
    }

    @GetMapping("/project/{projectID}")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable("projectID") UUID projectID) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectID));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") UUID id, @RequestBody UpdateTaskRequest request) {
        TaskDTO updateTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    //TODO: Пересмотреть данный метод. Метод отрабатывает правильно, но меня не устраивает запрос /{taskId}/{userEmail}/assignUser
    @PutMapping("/{taskId}/{userEmail}/assignUser")
    public ResponseEntity<TaskDTO> assignUserToTask(@PathVariable("taskId") UUID taskId, @PathVariable("userEmail") String userEmail) {
        return ResponseEntity.ok(taskService.assignUserToTask(taskId, userEmail));
    }

    @GetMapping("/byTags")
    public ResponseEntity<List<TaskDTO>> getTasksByTags(@RequestParam Set<String> tagNames) {
        List<TaskDTO> tasks = taskService.getTaskByTags(tagNames);
        return ResponseEntity.ok(tasks);
    }
}
