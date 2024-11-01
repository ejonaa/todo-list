package com.crispy.crispy_be_challenge_ejona_aliaj.controller.api;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security.AuthenticationRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security.NewUserRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.validation.ValidateInputRequest;
import com.crispy.crispy_be_challenge_ejona_aliaj.controller.response.AuthenticationResponse;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.converter.UserDtoConverter;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.AuthorizationException;
import com.crispy.crispy_be_challenge_ejona_aliaj.exception.LoginAlreadyUsedException;
import com.crispy.crispy_be_challenge_ejona_aliaj.security.jwt.TokenProvider;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final UserService userService;

    private final UserDtoConverter userDtoConverter;

    public AuthenticationController(AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, TokenProvider tokenProvider, UserDtoConverter userDtoConverter) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.userDtoConverter = userDtoConverter;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final String token = generateToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @ValidateInputRequest
    @PostMapping(value = "/create-account")
    public ResponseEntity<Void> createAccount(@RequestBody NewUserRequest newUserRequest) {
        if (userService.existsUsername(newUserRequest.getLogin())) {
            throw new LoginAlreadyUsedException("Username has already been taken");
        }

        userService.createUser(userDtoConverter.convert(newUserRequest), newUserRequest.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    private void authenticate(String username, String password) {
        try {
            authenticationManagerBuilder.getObject()
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new AuthorizationException("Bad Credentials");
        }
    }

    /**
     * Generates user token
     *
     * @param username username
     * @param password password
     */
    public String generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

}
