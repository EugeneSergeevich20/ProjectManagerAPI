package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * возвращается при запросах на получение задач
 */
public class TaskDTO {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private UUID projectId;
    private Set<String> tags;
}
