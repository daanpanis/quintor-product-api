package nl.quintor.dpanis.productapi;

import nl.quintor.dpanis.productapi.constants.AuthenticationConstants;
import nl.quintor.dpanis.productapi.entities.Privilege;
import nl.quintor.dpanis.productapi.entities.Role;
import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.repositories.PrivilegeRepository;
import nl.quintor.dpanis.productapi.repositories.RoleRepository;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static nl.quintor.dpanis.productapi.constants.AuthenticationConstants.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        alreadySetup = true;

        Map<String, Privilege> privileges = createPrivileges();

        createRoleIfNotFound(ROLE_USER, true,
                new HashSet<>(Arrays.asList(
                        privileges.get(PRIVILEGE_CART_MANAGE),
                        privileges.get(PRIVILEGE_CATEGORY_LIST),
                        privileges.get(PRIVILEGE_CATEGORY_GET),
                        privileges.get(PRIVILEGE_PRODUCT_GET),
                        privileges.get(PRIVILEGE_PRODUCT_LIST)
                )));

        createRoleIfNotFound(ROLE_MODERATOR, false,
                new HashSet<>(Arrays.asList(
                        privileges.get(PRIVILEGE_PRODUCT_CREATE),
                        privileges.get(PRIVILEGE_PRODUCT_UPDATE),
                        privileges.get(PRIVILEGE_PRODUCT_GET),
                        privileges.get(PRIVILEGE_PRODUCT_LIST),
                        privileges.get(PRIVILEGE_PRODUCT_DELETE),
                        privileges.get(PRIVILEGE_CATEGORY_CREATE),
                        privileges.get(PRIVILEGE_CATEGORY_UPDATE),
                        privileges.get(PRIVILEGE_CATEGORY_GET),
                        privileges.get(PRIVILEGE_CATEGORY_LIST),
                        privileges.get(PRIVILEGE_CATEGORY_DELETE),
                        privileges.get(PRIVILEGE_CART_MANAGE)
                )));

        Role adminRole = createRoleIfNotFound(ROLE_ADMINISTRATOR, false, new HashSet<>(privileges.values()));

        if (userRepository.findByEmail("admin@quintor.nl") == null) {
            userRepository.save(
                    new User(
                            "Admin",
                            "Account",
                            "admin@quintor.nl",
                            passwordEncoder.encode("adminpassword"),
                            true,
                            true,
                            Collections.singleton(adminRole),
                            null
                    )
            );
        }

        alreadySetup = true;
    }

    @Transactional
    Map<String, Privilege> createPrivileges() {
        return Stream.of(AuthenticationConstants.ALL_PRIVILEGES)
                .map(this::createPrivilegeIfNotFound)
                .collect(Collectors.toMap(Privilege::getName, privilege -> privilege));
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name, null);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, boolean defaultRole, Set<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name, defaultRole, null, privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
