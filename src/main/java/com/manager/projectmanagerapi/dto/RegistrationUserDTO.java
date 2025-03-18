package com.manager.projectmanagerapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationUserDTO {
    @NotNull(message = "Логин пользователя не может быть пустым")
    @Size(min = 2, max = 50, message = "Логин должен содержать от 2 до 50 символов")
    private String username;

    @NotNull(message = "Пароль обязателен")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;

    private String confirmPassword;

    @NotNull(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    private String email;
}
