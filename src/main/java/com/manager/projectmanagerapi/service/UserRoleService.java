package com.manager.projectmanagerapi.service;

import com.manager.projectmanagerapi.entity.UserRole;
import com.manager.projectmanagerapi.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole() {
        return userRoleRepository.findByName("ROLE_USER").get();
    }

}
