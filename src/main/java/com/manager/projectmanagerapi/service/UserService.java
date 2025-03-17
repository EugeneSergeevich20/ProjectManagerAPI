package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.dto.RegistrationUserDTO;
import com.manager.projectmanagerapi.dto.UserDTO;
import com.manager.projectmanagerapi.entity.User;
import com.manager.projectmanagerapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public UserDTO createUser(RegistrationUserDTO registrationUserDTO) {
        User user = User.builder()
                .username(registrationUserDTO.getUsername())
                .email(registrationUserDTO.getEmail())
                .password(passwordEncoder.encode(registrationUserDTO.getPassword()))
                .roles(List.of(userRoleService.getUserRole()))
                .build();
        return convertUserToDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserByUsernameDTO(User user) {
        return convertUserToDTO(getUserByUsername(user.getUsername()));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found", email)));
    }

    private UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
