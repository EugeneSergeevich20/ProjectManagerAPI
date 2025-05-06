package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * используется при создании нового проекта
 */
public class CreateProjectRequest {

    @NotBlank
    @NotNull(message = "Логин пользователя не может быть пустым")
    @Size(min = 2, max = 50, message = "Логин должен содержать от 2 до 50 символов")
    private String name;
    @Size(max = 1000, message = "Описание задачи не может превышать 1000 символов")
    private String description;
    @NotNull
    private ProjectStatus status;

}
