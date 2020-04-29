package nl.quintor.dpanis.productapi.controllers;

import nl.quintor.dpanis.productapi.entities.User;
import nl.quintor.dpanis.productapi.models.LoginModel;
import nl.quintor.dpanis.productapi.models.RegistrationModel;
import nl.quintor.dpanis.productapi.security.AuthToken;
import nl.quintor.dpanis.productapi.security.TokenProvider;
import nl.quintor.dpanis.productapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/generate-token")
    @ResponseStatus(HttpStatus.OK)
    public AuthToken generateToken(@Valid @RequestBody LoginModel model) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthToken(tokenProvider.generateToken(authentication));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid RegistrationModel model) {
        return this.userService.registerUser(model);
    }
}
