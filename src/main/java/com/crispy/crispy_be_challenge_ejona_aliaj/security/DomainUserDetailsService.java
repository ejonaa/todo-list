package com.crispy.crispy_be_challenge_ejona_aliaj.security;


import com.crispy.crispy_be_challenge_ejona_aliaj.entity.UserEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user's details given their userName.
     *
     * @param username username
     * @return UserDetails
     * @throws UsernameNotFoundException if username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByLogin(username);
        return user.map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found: " + username));
    }

    private User createSpringSecurityUser(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new User(
                user.getLogin(),
                user.getPasswordHash(),
                authorities);
    }

}
