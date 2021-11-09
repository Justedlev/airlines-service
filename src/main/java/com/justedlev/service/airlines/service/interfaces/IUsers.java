package com.justedlev.service.airlines.service.interfaces;

import com.justedlev.service.airlines.api.Code;
import com.justedlev.service.airlines.api.dto.UserDTO;

import java.util.List;

public interface IUsers {

    List<UserDTO> getAccounts();

    UserDTO getAccount(String nickname);

    Code addAccount(String nickname, String password);

    Code deleteAccount(String nickname, String password);

    Code addRole(String role, String adminNickname, String adminPassword);

}
