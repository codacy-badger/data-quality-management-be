package com.bbahaida.dataqualitymanagement.security;

import com.bbahaida.dataqualitymanagement.entities.AppUser;
import com.bbahaida.dataqualitymanagement.services.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = accountService.loadUserByUsername(username);

        if (user == null) throw new UsernameNotFoundException("Invalid username");
        if (!user.isActive()) throw new UsernameNotFoundException("appUser is not active please contact an administrator");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRole())));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
