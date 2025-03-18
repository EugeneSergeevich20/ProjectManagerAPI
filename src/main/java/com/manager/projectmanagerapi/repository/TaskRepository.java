package com.manager.projectmanagerapi.repository;

import com.manager.projectmanagerapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByProjectId(UUID projectId);
    @Query("SELECT t FROM Task t JOIN t.tags tag WHERE tag.name IN :tagNames")
    List<Task> findByTags(@Param("tagNames")Set<String> tags);
}
