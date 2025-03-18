package com.manager.projectmanagerapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "email")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<UserRole> roles;

    //@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    //private Set<Project> ownedProjects = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    @MapKey(name = "name")
    private Map<String, Project> participatingProjects = new HashMap<>();

    //@OneToMany(mappedBy = "assignee")
    //private Set<Task> assignedTasks = new HashSet<>();
}
