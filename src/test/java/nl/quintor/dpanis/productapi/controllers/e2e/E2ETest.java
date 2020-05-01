package nl.quintor.dpanis.productapi.controllers.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import nl.quintor.dpanis.productapi.entities.Privilege;
import nl.quintor.dpanis.productapi.entities.Role;
import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.repositories.PrivilegeRepository;
import nl.quintor.dpanis.productapi.repositories.RoleRepository;
import nl.quintor.dpanis.productapi.repositories.UserRepository;
import nl.quintor.dpanis.productapi.security.TokenProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application.test.properties")
public abstract class E2ETest {

    @AfterAll
    static void tearDownClass() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        privilegeRepository.deleteAll();
        authorizedToken = null;
        unauthorizedToken = null;
        userRepository = null;
        roleRepository = null;
        privilegeRepository = null;
    }

    @BeforeAll
    static void setupClass() {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (type, s) -> new ObjectMapper()
        ));
    }

    protected static UserRepository userRepository;
    protected static RoleRepository roleRepository;
    protected static PrivilegeRepository privilegeRepository;
    protected static String authorizedToken;
    protected static String unauthorizedToken;
    protected static final String authorizedEmail = "authorized@user.com";
    protected static final String authorizedPassword = "authorized";
    protected static final String unauthorizedEmail = "unauthorized@user.com";
    protected static final String unauthorizedPassword = "unauthorized";


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    private final String[] authorizedUserAuthorities;
    @Value("${jwt.token-header}")
    protected String tokenHeader;
    @Value("${jwt.token-prefix}")
    protected String tokenPrefix;
    @LocalServerPort
    protected int port;


    E2ETest(UserRepository userRepo, RoleRepository roleRepo, PrivilegeRepository privilegeRepo, String... authorizedUserAuthorities) {
        this.authorizedUserAuthorities = authorizedUserAuthorities;
        userRepository = userRepo;
        roleRepository = roleRepo;
        privilegeRepository = privilegeRepo;
    }

    @BeforeEach
    protected void setupUsersAndTokens() {
        if (authorizedToken != null) {
            return;
        }

        userRepository.deleteAll();
        roleRepository.deleteAll();
        privilegeRepository.deleteAll();

        Collection<Privilege> privileges = createPrivileges();
        Privilege privilege = new Privilege("unauthorized", null);
        privilegeRepository.save(privilege);

        Role authorizedRole = createRole("authorized", privileges);
        Role unauthorizedRole = createRole("unauthorized", Collections.singletonList(privilege));

        createUser("Authorized", authorizedEmail, authorizedPassword, authorizedRole);
        createUser("Unauthorized", unauthorizedEmail, unauthorizedPassword, unauthorizedRole);

        authorizedToken = tokenPrefix + " " + tokenProvider.generateToken(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizedEmail, authorizedPassword)));
        unauthorizedToken = tokenPrefix + " " + tokenProvider.generateToken(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(unauthorizedEmail, unauthorizedPassword)));
    }

    void createUser(String firstName, String email, String password, Role role) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName("User");
        user.setEnabled(true);
        user.setTokenExpired(true);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    Collection<Privilege> createPrivileges() {
        Collection<Privilege> privileges = Stream.of(authorizedUserAuthorities).map(s -> {
            Privilege privilege = new Privilege();
            privilege.setName(s);
            return privilege;
        }).collect(Collectors.toSet());
        privilegeRepository.saveAll(privileges);
        return privileges;
    }

    Role createRole(String name, Collection<Privilege> privileges) {
        Role role = new Role();
        role.setName(name);
        role.setPrivileges(privileges);
        roleRepository.save(role);
        return role;
    }

    protected String url(String path) {
        return String.format("http://127.0.0.1:%s/%s", port, path);
    }

    protected RequestSpecification givenAuthorized() {
        return givenWithToken(authorizedToken);
    }

    protected RequestSpecification givenUnauthorized() {
        return givenWithToken(unauthorizedToken);
    }

    protected RequestSpecification givenWithToken(String token) {
        return givenJson().header(tokenHeader, token);
    }

    protected RequestSpecification givenJson() {
        return given().header("Content-Type", "application/json");
    }
}
