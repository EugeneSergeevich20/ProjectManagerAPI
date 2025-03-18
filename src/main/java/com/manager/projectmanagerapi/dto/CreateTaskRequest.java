package com.manager.projectmanagerapi.dto;

import com.manager.projectmanagerapi.entity.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTaskRequest {

    @NotBlank
    @NotNull(message = "Название задачи не может быть пустым")
    @Size(min = 3, max = 100, message = "Название задачи должно быть от 3 до 100 символов")
    private String title;

    @Size(max = 1000, message = "Описание задачи не может превышать 1000 символов")
    private String description;

    @NotNull(message = "Дата дедлайна обязательна")
    @Future(message = "Дата дедлайна должна быть в будущем")
    private LocalDateTime deadline;

    @NotNull
    private UUID projectId;
    
    private Set<String> tags;
}
