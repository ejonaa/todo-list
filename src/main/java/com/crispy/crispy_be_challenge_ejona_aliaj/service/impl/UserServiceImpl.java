package com.crispy.crispy_be_challenge_ejona_aliaj.service.impl;

import com.crispy.crispy_be_challenge_ejona_aliaj.converter.UserMapper;
import com.crispy.crispy_be_challenge_ejona_aliaj.dto.UserDTO;
import com.crispy.crispy_be_challenge_ejona_aliaj.entity.UserEntity;
import com.crispy.crispy_be_challenge_ejona_aliaj.repository.UserRepository;
import com.crispy.crispy_be_challenge_ejona_aliaj.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsUsername(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    @Override
    public void createUser(UserDTO user, String password) {
        UserEntity newUser = userMapper.fromDto(user);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setCreatedDate(LocalDateTime.now());
        userRepository.save(newUser);
    }

}
