package nl.quintor.dpanis.productapi.services.implementations;

import com.google.common.base.Preconditions;
import nl.quintor.dpanis.productapi.entities.Role;
import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.exceptions.EmailExistsException;
import nl.quintor.dpanis.productapi.exceptions.NoDefaultRoleException;
import nl.quintor.dpanis.productapi.models.RegistrationModel;
import nl.quintor.dpanis.productapi.repositories.RoleRepository;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import nl.quintor.dpanis.productapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    Expects model to be valid
     */
    @Override
    public User registerUser(@Valid RegistrationModel model) {
        Preconditions.checkNotNull(model);
        if (userRepository.findByEmail(model.getEmail()) != null) {
            throw new EmailExistsException("User by email " + model.getEmail() + " already exists");
        }

        Collection<Role> defaultRoles = roleRepository.defaultRoleTrue();
        if (defaultRoles == null || defaultRoles.isEmpty()) {
            throw new NoDefaultRoleException("No default roles are defined in the database!");
        }

        User user = new User();
        user.setEmail(model.getEmail());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        user.setEnabled(true);
        user.setTokenExpired(true);
        user.setRoles(defaultRoles);
        userRepository.save(user);
        return user;
    }
}
