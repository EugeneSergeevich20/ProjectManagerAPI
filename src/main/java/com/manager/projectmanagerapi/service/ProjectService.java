package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.dto.CreateProjectRequest;
import com.manager.projectmanagerapi.dto.ProjectDTO;
import com.manager.projectmanagerapi.dto.UpdateProjectRequest;
import com.manager.projectmanagerapi.entity.Project;
import com.manager.projectmanagerapi.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * Все проекты
     * @return
     */
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает проект по его ID
     * @param projectId
     * @return
     */
    public ProjectDTO getProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        return convertToDTO(project);
    }

    /**
     * Добавление новой задачи
     * @param request
     * @return
     */
    public ProjectDTO createProject(CreateProjectRequest request) {
        if (projectRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Project name already exists");
        }

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();

        return convertToDTO(projectRepository.save(project));
    }

    /**
     * Обновление данных проекта
     * @param projectId
     * @param request
     * @return
     */
    public ProjectDTO updateProject(UUID projectId, UpdateProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        if (request.getName() != null) project.setName(request.getName());
        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getStatus() != null) project.setStatus(request.getStatus());

        return convertToDTO(projectRepository.save(project));
    }

    /**
     * Удаление проекта
     * @param projectId
     */
    public void deleteProject(UUID projectId) {
        if (!projectRepository.existsById(projectId))
            throw new EntityNotFoundException("Project not found");
        projectRepository.deleteById(projectId);
    }

    /**
     * Конвертация в DTO
     * @param project
     * @return
     */
    private ProjectDTO convertToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
    }

}
