package com.justedlev.service.airlines.rest;

import com.justedlev.service.airlines.api.PathConstants;
import com.justedlev.service.airlines.api.ServiceNamesConstants;
import com.justedlev.service.airlines.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(PathConstants.USERS_PATH)
public class UsersController {

    @Autowired
    @Qualifier(ServiceNamesConstants.USERS_SERVICE_NAME)
    private UsersService service;

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam(required = false) String nickname) {
        logDegug("", nickname);
        if (nickname != null) {
            return ResponseEntity.ok(service.getAccount(nickname));
        }
        return ResponseEntity.ok(service.getAccounts());
    }

    @PostMapping(PathConstants.ADD_PATH)
    public ResponseEntity<?> addUser(@RequestParam String nickname, @RequestParam String password) {
        logDegug(PathConstants.ADD_PATH, nickname, password);
        return ResponseEntity.ok(service.addAccount(nickname, password));
    }

    @DeleteMapping(PathConstants.DELETE_PATH)
    public ResponseEntity<?> deleteUser(@RequestParam String nickname, @RequestParam String password) {
        logDegug(PathConstants.DELETE_PATH, nickname, password);
        return ResponseEntity.ok(service.deleteAccount(nickname, password));
    }

    @PostMapping(PathConstants.ROLES_ADD_PATH)
    public ResponseEntity<?> addRole(@RequestParam String role, @RequestParam String nickname,
            @RequestParam String password) {
        logDegug(PathConstants.ROLES_ADD_PATH, role, nickname, password);
        return ResponseEntity.ok(service.addRole(role, nickname, password));
    }

    private void logDegug(String path, Object... values) {
        log.debug("Request: '{}{}' with values: {}", PathConstants.USERS_PATH, path, values);
    }

}
