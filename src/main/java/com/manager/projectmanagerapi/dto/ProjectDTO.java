package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.ProjectStatus;
import com.manager.projectmanagerapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Возвращается при запросах на получение проектов
 */
public class ProjectDTO {
    private UUID id;
    private String name;
    private String description;
    private ProjectStatus status;
    //private Map<String, User> participants;
}
