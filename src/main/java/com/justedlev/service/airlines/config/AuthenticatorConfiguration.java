package com.justedlev.service.airlines.config;

import com.justedlev.service.airlines.repository.UsersRepository;
import com.justedlev.service.airlines.repository.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AuthenticatorConfiguration implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity account = usersRepository.getByName(username).orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException(String.format("Account by name '%s' not found", username));
        }
        String[] roles = account.getRoles().stream()
                .map(r -> "ROLE_" + r.getRole())
                .toArray(String[]::new);
        return new User(username, account.getHashCode(), AuthorityUtils.createAuthorityList(roles));
    }

}
