package nl.quintor.dpanis.productapi.services.implementations;

import nl.quintor.dpanis.productapi.entities.Privilege;
import nl.quintor.dpanis.productapi.entities.Role;
import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Credits go to: https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 */
@Service("customUserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Couldn't find user by email " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getPrivileges().stream())
                .map(Privilege::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
