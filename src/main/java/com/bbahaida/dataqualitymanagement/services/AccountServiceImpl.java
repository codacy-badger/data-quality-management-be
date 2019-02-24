package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.AppRole;
import com.bbahaida.dataqualitymanagement.entities.AppUser;
import com.bbahaida.dataqualitymanagement.exceptions.UserAlreadyExistsException;
import com.bbahaida.dataqualitymanagement.exceptions.UserNotFoundException;
import com.bbahaida.dataqualitymanagement.repositories.AppRoleRepository;
import com.bbahaida.dataqualitymanagement.repositories.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private AppUserRepository userRepository;
    private AppRoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository userRepository, AppRoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {

        AppUser user = userRepository.findByUsername(username);
        if (user != null) throw new UserAlreadyExistsException("Username already exist!!");
        if ( !password.equals(confirmedPassword) ) throw new RuntimeException("Please confirm your password");

        AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setUsername(username);
        appUser.setActive(false);
        userRepository.save(appUser);
        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = userRepository.findByUsername(username);
        AppRole appRole = roleRepository.findByRole(role);

        appUser.getRoles().add(appRole);
    }

    @Override
    public void activateUser(Long id) {
        AppUser user = userRepository.getOne(id);
        if (user!=null){
            user.setActive(true);
            userRepository.save(user);
            return;
        }
        throw new UserNotFoundException("User not exist");

    }

    @Override
    public Long getUserId(String username) {

        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        }
        throw new UserNotFoundException("User not exist");
    }

}
