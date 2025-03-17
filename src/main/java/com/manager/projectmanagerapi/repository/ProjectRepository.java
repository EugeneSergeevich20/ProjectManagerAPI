package com.manager.projectmanagerapi.repository;

import com.manager.projectmanagerapi.entity.Project;
import com.manager.projectmanagerapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByName(String name);
    List<Project> findByOwnerId(UUID id);
    List<Project> findByParticipants(Map<String, User> participants);
}
