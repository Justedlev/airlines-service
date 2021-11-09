package com.justedlev.service.airlines.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.justedlev.service.airlines.api.PathConstants.*;

@Configuration
public class AirlinesSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String[] accessAdminPaths = { ROLES_PATH, ROLES_ADD_PATH, ROLES_DELETE_PATH, USERS_PATH,
            USERS_ADD_PATH, USERS_DELETE_PATH, AIRLINES_PATH, AIRLINES_ADD_PATH, AIRLINES_DELETE_PATH,
            AIRLINES_AIRCRAFT_PATH, AIRLINES_AIRCRAFT_ADD_PATH, AIRLINES_AIRCRAFT_SELL_PATH,
            AIRLINES_AIRCRAFT_DELETE_PATH, AIRLINES_DESTINATIONS_PATH, AIRLINES_DESTINATIONS_ALL_PATH,
            AIRLINES_DESTINATIONS_ADD_PATH, AIRLINES_DESTINATIONS_DELETE_PATH, AIRCRAFT_PATH, AIRCRAFT_ADD_PATH,
            AIRCRAFT_SELL_PATH, AIRCRAFT_DELETE_PATH, DESTINATIONS_PATH, DESTINATIONS_ALL_PATH, DESTINATIONS_ADD_PATH,
            DESTINATIONS_DELETE_PATH };

    private final String[] accessUserPaths = { AIRLINES_PATH, AIRLINES_DESTINATIONS_PATH };

    @Value("${com.justedlev.service.airlines.access_only:admin}")
    private String accessOnly;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().headers().frameOptions().disable().and().formLogin().and().httpBasic().and();
        if (accessOnly.equals("any")) {
            httpSecurity.authorizeRequests().anyRequest().permitAll();
        }
        if (accessOnly.equals("user")) {
            httpSecurity.authorizeRequests().antMatchers(accessAdminPaths).hasAnyRole("USER")
                    .antMatchers(AIRLINES_DESTINATIONS_PATH, AIRLINES_PATH).hasAnyRole("USER");
        }
        if (accessOnly.equals("admin")) {
            httpSecurity.authorizeRequests().antMatchers(accessAdminPaths).hasAnyRole("ADMIN")
                    .antMatchers(accessUserPaths).hasAnyRole("ADMIN", "USER");
        }
        httpSecurity.authorizeRequests().antMatchers(USERS_ADD_PATH, "/swagger-ui.html").permitAll();
    }

}
