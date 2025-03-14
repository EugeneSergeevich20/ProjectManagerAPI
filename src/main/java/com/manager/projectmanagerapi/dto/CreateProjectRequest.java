package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * используется при создании нового проекта
 */
public class CreateProjectRequest {

    @NotBlank
    private String name;
    private String description;
    @NotNull
    private ProjectStatus status;

}
