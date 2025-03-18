package com.manager.projectmanagerapi.controller;

import com.manager.projectmanagerapi.dto.CreateTaskRequest;
import com.manager.projectmanagerapi.dto.TaskDTO;
import com.manager.projectmanagerapi.dto.UpdateTaskRequest;
import com.manager.projectmanagerapi.exception.UserUnauthorizedException;
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

    /*Получение всех задач и задач по критериям*/

    @GetMapping("/project/{projectID}")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable("projectID") UUID projectID) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectID));
    }

    @GetMapping("/byTags")
    public ResponseEntity<List<TaskDTO>> getTasksByTags(@RequestParam Set<String> tagNames) {
        List<TaskDTO> tasks = taskService.getTaskByTags(tagNames);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/byUser")
    public ResponseEntity<?> getTasksByUser(){
        List<TaskDTO> tasks;
        try {
            tasks = taskService.getTasksByUser();
        } catch (UserUnauthorizedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(tasks);
    }

    /*Добавление задачи*/

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskRequest request) {
        TaskDTO createTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTask);
    }

    /*Обновление задачи*/

    @PutMapping("/{id}/update")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") UUID id, @RequestBody UpdateTaskRequest request) {
        TaskDTO updateTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updateTask);
    }

    //TODO: Пересмотреть данный метод. Метод отрабатывает правильно, но меня не устраивает запрос /{taskId}/{userEmail}/assignUser
    @PutMapping("/{taskId}/{userEmail}/assignUser")
    public ResponseEntity<TaskDTO> assignUserToTask(@PathVariable("taskId") UUID taskId, @PathVariable("userEmail") String userEmail) {
        return ResponseEntity.ok(taskService.assignUserToTask(taskId, userEmail));
    }

    /*Удаление задачи*/

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }




}
