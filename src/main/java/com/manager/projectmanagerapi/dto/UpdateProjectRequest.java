package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.ProjectStatus;
import com.manager.projectmanagerapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private Set<String> emailParticipants;
}
