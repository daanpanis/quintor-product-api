package nl.quintor.dpanis.productapi.services.implementations;

import nl.quintor.dpanis.productapi.entities.Role;
import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.exceptions.EmailExistsException;
import nl.quintor.dpanis.productapi.exceptions.NoDefaultRoleException;
import nl.quintor.dpanis.productapi.models.RegistrationModel;
import nl.quintor.dpanis.productapi.repositories.RoleRepository;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserServiceImpl expects a validated RegistrationModel so will not check for any invalid values inside the model.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    UserServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void registerSuccess() {
        when(roleRepository.defaultRoleTrue()).thenReturn(Collections.singletonList(new Role()));
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");

        RegistrationModel model = defaultModel();
        User user = service.registerUser(model);
        assertNotNull(user);
        assertEquals(model.getEmail(), user.getEmail());
        assertEquals(model.getFirstName(), user.getFirstName());
        assertEquals(model.getLastName(), user.getLastName());
        assertEquals(user.getPassword(), "encoded_password");
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        verify(roleRepository, times(1)).defaultRoleTrue();
        verify(userRepository, times(1)).findByEmail(model.getEmail());
        verify(passwordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(any());
    }


    @Test
    void registerExistingUser() {
        when(userRepository.findByEmail(any())).thenReturn(new User());

        RegistrationModel model = defaultModel();
        assertThrows(EmailExistsException.class, () -> service.registerUser(model));
        verify(userRepository, times(1)).findByEmail(model.getEmail());
        verify(roleRepository, times(0)).defaultRoleTrue();
        verify(passwordEncoder, times(0)).encode(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void registerNoDefaultRows() {
        when(roleRepository.defaultRoleTrue()).thenReturn(new ArrayList<>());
        RegistrationModel model = defaultModel();
        assertThrows(NoDefaultRoleException.class, () -> service.registerUser(model));
        verify(userRepository, times(1)).findByEmail(model.getEmail());
        verify(roleRepository, times(1)).defaultRoleTrue();
        verify(passwordEncoder, times(0)).encode(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void registerNullModel() {
        assertThrows(NullPointerException.class, () -> service.registerUser(null));
        verify(userRepository, times(0)).findByEmail(any());
        verify(roleRepository, times(0)).defaultRoleTrue();
        verify(passwordEncoder, times(0)).encode(any());
        verify(userRepository, times(0)).save(any());
    }

    private RegistrationModel defaultModel() {
        RegistrationModel model = new RegistrationModel();
        model.setFirstName("Test");
        model.setLastName("User");
        model.setEmail("test@user.com");
        model.setPassword("password1!");
        model.setConfirmPassword("password1!");
        return model;
    }
}