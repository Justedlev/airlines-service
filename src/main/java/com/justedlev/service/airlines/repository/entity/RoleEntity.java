package com.justedlev.service.airlines.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<UserEntity> users;

    public RoleEntity(String role) {
        this.role = role;
        this.users = new HashSet<>();
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
