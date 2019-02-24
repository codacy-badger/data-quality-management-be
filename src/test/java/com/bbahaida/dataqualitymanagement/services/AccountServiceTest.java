package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.AppUser;
import com.bbahaida.dataqualitymanagement.exceptions.UserNotFoundException;
import com.bbahaida.dataqualitymanagement.repositories.AppRoleRepository;
import com.bbahaida.dataqualitymanagement.repositories.AppUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AppUserRepository userRepository;
    @Mock
    private AppRoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService service = new AccountServiceImpl(userRepository, roleRepository, passwordEncoder);

    @Test(expected = UserNotFoundException.class)
    public void activateUser_ShouldThrowRuntimeException() throws Exception {
        when(userRepository.getOne(1L)).thenReturn(null);

        service.activateUser(1L);

        verify(userRepository).getOne(1L);
    }

    @Test
    public void getUserId_ShouldReturn1_IfUsernameIsBrahim() throws Exception {
        String username = "brahim";
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);
        Long expected = 1L;

        Long actual = service.getUserId(username);

        assertEquals(expected, actual);
        verify(userRepository).findByUsername(username);

    }

    @Test(expected = UserNotFoundException.class)
    public void getUserId_ShouldThrowUserNotFoundException() throws Exception {
        String username = "brahim";
        when(userRepository.findByUsername(username)).thenReturn(null);

        service.getUserId(username);

        verify(userRepository).findByUsername(username);
    }

}
