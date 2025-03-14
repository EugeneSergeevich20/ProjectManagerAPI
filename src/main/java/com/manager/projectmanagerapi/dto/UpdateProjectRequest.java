package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 *  для обновления проекта
 */
public class UpdateProjectRequest {
    private String name;
    private String description;
    private ProjectStatus status;
}
