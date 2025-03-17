package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateTaskRequest {
    @NotBlank
    private String title;
    private String description;
    /*@NotNull
    private TaskStatus status;*/
    @NotNull
    private UUID projectId;
    private Set<String> tags;
}
