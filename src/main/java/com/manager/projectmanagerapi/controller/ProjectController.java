package com.manager.projectmanagerapi.controller;

import com.manager.projectmanagerapi.dto.CreateProjectRequest;
import com.manager.projectmanagerapi.dto.ProjectDTO;
import com.manager.projectmanagerapi.dto.UpdateProjectRequest;
import com.manager.projectmanagerapi.dto.UserDTO;
import com.manager.projectmanagerapi.exception.AppError;
import com.manager.projectmanagerapi.exception.UserUnauthorizedException;
import com.manager.projectmanagerapi.service.AuthService;
import com.manager.projectmanagerapi.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectRequest request) {
        ProjectDTO createProject = null;
        try {
            createProject = projectService.createProject(request);
        } catch (UserUnauthorizedException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable UUID id, @RequestBody @Valid UpdateProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/addParticipant")
    public ResponseEntity<?> addParticipant(@PathVariable("projectId") UUID projectId, @RequestBody @Valid UpdateProjectRequest request) {
        try {
            return ResponseEntity.ok(projectService.addParticipant(projectId, request));
        } catch (UserUnauthorizedException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //TODO: Пересмотреть методы по получению проектов пользователя. Протестировать.
    @GetMapping("/owner")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUserId() throws UserUnauthorizedException {
        return ResponseEntity.ok(projectService.getProjectsByUser());
    }

    @GetMapping("/participant")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUserParticipants() throws UserUnauthorizedException {
        return ResponseEntity.ok(projectService.getProjectsByUserParticipants());
    }

    @GetMapping("/allUsersProjects")
    public ResponseEntity<List<ProjectDTO>> getAllUsersProjects() throws UserUnauthorizedException {
        return ResponseEntity.ok(projectService.getAllUsersProjects());
    }

}
