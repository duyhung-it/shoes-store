package com.shoes.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoes.domain.User;
import com.shoes.security.jwt.JWTFilter;
import com.shoes.security.jwt.TokenProvider;
import com.shoes.service.UserService;
import com.shoes.web.rest.vm.LoginVM;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.security.RandomUtil;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@RequestBody LoginVM loginVM) {
        System.out.println(loginVM);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getLogin(),
            loginVM.getPasswordHash()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/registerOauth2")
    public ResponseEntity<JWTToken> registerOauth2(OAuth2AuthenticationToken token) {
        String email = token.getPrincipal().getAttribute("email");
        String login = RandomUtil.generateResetKey();
        User user = new User();
        if(userService.findOneByEmailIgnoreCase(email).isPresent()){
            user = userService.findOneByEmailIgnoreCase(email).get();
        }else{
            user.setEmail(token.getPrincipal().getAttribute("email"));
            user.setLogin(login);
            user.setFirstName(token.getPrincipal().getAttribute("name"));
            user.setActivated(true);
            user.setPassword(passwordEncoder.encode(login));
            user = userService.save(user);
        }
        LoginVM loginVM = new LoginVM();
        loginVM.setLogin(user.getLogin());
        loginVM.setPasswordHash(user.getPassword());
        loginVM.setRememberMe(false);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getLogin(),
            login
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
