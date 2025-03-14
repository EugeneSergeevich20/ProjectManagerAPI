package com.manager.projectmanagerapi.repository;

import com.manager.projectmanagerapi.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {
    Optional<UserRole> findByName(String name);
}
