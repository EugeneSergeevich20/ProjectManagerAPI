package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.dto.CreateProjectRequest;
import com.manager.projectmanagerapi.dto.ProjectDTO;
import com.manager.projectmanagerapi.dto.UpdateProjectRequest;
import com.manager.projectmanagerapi.dto.UserDTO;
import com.manager.projectmanagerapi.entity.Project;
import com.manager.projectmanagerapi.entity.User;
import com.manager.projectmanagerapi.exception.UserUnauthorizedException;
import com.manager.projectmanagerapi.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final UserService userService;

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
     * Проекты пользователя, которые он создал
     * @return
     * @throws UserUnauthorizedException
     */
    public List<ProjectDTO> getProjectsByUser() throws UserUnauthorizedException {
        User user = authService.getCurrentUser();
        return projectRepository.findByOwnerId(user.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Проекты, где пользователь участник
     * @return
     * @throws UserUnauthorizedException
     */
    public List<ProjectDTO> getProjectsByUserParticipants() throws UserUnauthorizedException {
        User user = authService.getCurrentUser();
        List<ProjectDTO> projects = new ArrayList<>();
        for (Project project : user.getParticipatingProjects().values()){
            if (!project.getOwner().getId().equals(user.getId()))
                projects.add(convertToDTO(project));
        }
        return projects;
    }

    /**
     * Все проекты пользователя(которые создал он и где он участвует)
     * @return
     * @throws UserUnauthorizedException
     */
    public List<ProjectDTO> getAllUsersProjects() throws UserUnauthorizedException {
        List<ProjectDTO> projects = new ArrayList<>();
        projects.addAll(getProjectsByUser());
        projects.addAll(getProjectsByUserParticipants());
        return projects;
    }

    /**
     * Добавление новой задачи
     * @param request
     * @return
     */
    @Transactional
    public ProjectDTO createProject(CreateProjectRequest request) throws UserUnauthorizedException {
        if (projectRepository.findByName(request.getName()).isPresent()) {
            /*TODO: Сейчас это для все проектов, когда добавятся пользователь постараться переделать под конкретного пользователя.
                Чтобы пользователь не мог добавить проект с наименованием, которое уже есть у него.
             */
            throw new IllegalArgumentException("Project name already exists");
        }

        User user = authService.getCurrentUser();

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus())
                .owner(user)
                .participants(new HashMap<>())
                .build();
        project.getParticipants().put(user.getUsername(), user);

        return convertToDTO(projectRepository.save(project));
    }

    /**
     * Добавляет участников в проект
     * @param projectId
     * @param request
     * @return
     * @throws UserUnauthorizedException
     */
    @Transactional
    public ProjectDTO addParticipant(UUID projectId, UpdateProjectRequest request) throws UserUnauthorizedException {
        User user = authService.getCurrentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        if (!project.getOwner().equals(user)) {
            throw new UserUnauthorizedException("You do not have permission to add this project");
        }

        Set<String> emailParticipants = request.getEmailParticipants();
        Map<String, User> participants = project.getParticipants();

        for (String emailParticipant : emailParticipants) {
            User userParticipant = userService.getUserByEmail(emailParticipant);
            if (!participants.containsKey(emailParticipant)) {
                participants.put(emailParticipant, userParticipant);
            }
        }

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
