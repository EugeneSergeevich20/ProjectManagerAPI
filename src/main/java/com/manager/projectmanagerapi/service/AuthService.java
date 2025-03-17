package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.dto.JwtRequest;
import com.manager.projectmanagerapi.dto.JwtResponse;
import com.manager.projectmanagerapi.dto.RegistrationUserDTO;
import com.manager.projectmanagerapi.dto.UserDTO;
import com.manager.projectmanagerapi.entity.User;
import com.manager.projectmanagerapi.exception.AppError;
import com.manager.projectmanagerapi.exception.UserUnauthorizedException;
import com.manager.projectmanagerapi.security.UserServiceImpl;
import com.manager.projectmanagerapi.utils.JwtTokenUtils;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceImpl userServiceImpl;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userServiceImpl.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userServiceImpl.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = userService.createUser(registrationUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    public User getCurrentUser() throws UserUnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( !authentication.isAuthenticated() || authentication.getPrincipal() == "anonymousUser")
            throw new UserUnauthorizedException("Пользователь не авторизован");

        //TODO: Протестировать с проектами и задачами. В дальнейшем может быть переделать.
        return userService.getUserByUsername(authentication.getName());
    }
}
