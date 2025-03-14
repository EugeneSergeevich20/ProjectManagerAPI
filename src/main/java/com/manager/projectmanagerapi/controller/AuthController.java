package com.manager.projectmanagerapi.controller;

import com.manager.projectmanagerapi.dto.JwtRequest;
import com.manager.projectmanagerapi.dto.RegistrationUserDTO;
import com.manager.projectmanagerapi.entity.User;
import com.manager.projectmanagerapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/generateToken")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        return authService.createNewUser(registrationUserDTO);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() throws AccessDeniedException {
        return authService.getCurrentUser();
    }

}
