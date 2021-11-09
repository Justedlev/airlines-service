package com.justedlev.service.airlines.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nickname;
    @Column(name = "hash_code")
    private String hashCode;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roles;

    public UserEntity(String nickname, String hashCode) {
        this.nickname = nickname;
        this.hashCode = hashCode;
        this.roles = new HashSet<>();
    }

    public UserEntity(String nickname, String hashCode, Set<RoleEntity> roles) {
        this.nickname = nickname;
        this.hashCode = hashCode;
        this.roles = roles;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
