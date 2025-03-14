package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private Set<String> tags;
}
