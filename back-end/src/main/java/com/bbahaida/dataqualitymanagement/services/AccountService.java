package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.AppRole;
import com.bbahaida.dataqualitymanagement.entities.AppUser;

public interface AccountService {

    AppUser saveUser(String username, String password, String confirmedPassword);
    AppRole saveRole(AppRole role);
    AppUser loadUserByUsername(String username);
    void activateUser(Long id);
    void addRoleToUser(String username, String role);
    Long getUserId(String username);

}
