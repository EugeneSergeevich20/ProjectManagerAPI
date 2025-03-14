package com.manager.projectmanagerapi.dto;


import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
