package com.justedlev.service.airlines.service;

import com.justedlev.service.airlines.api.Code;
import com.justedlev.service.airlines.api.ServiceNamesConstants;
import com.justedlev.service.airlines.api.dto.UserDTO;
import com.justedlev.service.airlines.repository.UsersRepository;
import com.justedlev.service.airlines.repository.RolesRepository;
import com.justedlev.service.airlines.repository.entity.UserEntity;
import com.justedlev.service.airlines.repository.entity.RoleEntity;
import com.justedlev.service.airlines.service.exceptions.UserAlreadyExistsException;
import com.justedlev.service.airlines.service.exceptions.UserAuthorizationException;
import com.justedlev.service.airlines.service.exceptions.UserNotExistsException;
import com.justedlev.service.airlines.service.interfaces.IUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Qualifier(ServiceNamesConstants.USERS_SERVICE_NAME)
public class UsersService implements IUsers {

    @Autowired
    private UsersRepository accountsRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<UserDTO> getAccounts() {
        return accountsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getAccount(String nickname) {
        UserEntity account = accountsRepository.getByName(nickname).orElse(null);
        if (account == null) {
            throw new UserNotExistsException(String.format("nickname '%s'", nickname));
        }
        return convertToDto(account);
    }

    @Override
    public Code addAccount(String nickname, String password) {
        UserEntity account = accountsRepository.getByName(nickname).orElse(null);
        if (account != null) {
            throw new UserAlreadyExistsException(String.format("nickname '%s'", nickname));
        }
        RoleEntity role = addRole("USER");
        account = new UserEntity(nickname, encoder.encode(password), Set.of(role));
        role.getUsers().add(account);
        accountsRepository.save(account);
        return Code.OK;
    }

    @Override
    public Code deleteAccount(String nickname, String password) {
        UserEntity account = accountsRepository.getByName(nickname).orElse(null);
        if (account == null) {
            throw new UserNotExistsException(String.format("nickname '%s'", nickname));
        }
        if(!encoder.matches(account.getHashCode(), password)) {
            throw new UserAuthorizationException("Incorrect user data");
        }
        accountsRepository.delete(account);
        return Code.OK;
    }

    @Override
    public Code addRole(String role, String adminNickname, String adminPassword) {
        UserEntity account = accountsRepository.getByName(adminNickname).orElse(null);
        if (account == null) {
            throw new UserNotExistsException(String.format("nickname '%s'", adminNickname));
        }
        if(!encoder.matches(account.getHashCode(), adminPassword)) {
            throw new UserAuthorizationException("Incorrect admin data");
        }
        addRole(role);
        return Code.OK;
    }

    private RoleEntity addRole(String role) {
        RoleEntity roleEntity = rolesRepository.getByRole(role.toUpperCase()).orElse(null);
        if(roleEntity != null) {
            return roleEntity;
        }
        roleEntity = new RoleEntity(role.toUpperCase());
        return rolesRepository.save(roleEntity);
    }

    private UserDTO convertToDto(UserEntity entity) {
        return UserDTO.builder()
                .nickname(entity.getNickname())
                .hashCode(entity.getHashCode())
                .roles(entity.getRoles().stream()
                        .map(RoleEntity::getRole)
                        .collect(Collectors.toSet()))
                .build();
    }

}
