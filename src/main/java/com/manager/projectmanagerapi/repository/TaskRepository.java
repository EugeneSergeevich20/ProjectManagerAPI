package com.manager.projectmanagerapi.repository;

import com.manager.projectmanagerapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByProjectId(UUID projectId);
}
