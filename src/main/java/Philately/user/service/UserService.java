package Philately.user.service;


import Philately.user.model.User;
import Philately.user.repository.UserRepository;
import Philately.web.dto.LoginRequest;
import Philately.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest registerRequest) {

       Optional<User> optionalUser = userRepository.findByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail());
       if (optionalUser.isPresent()) {
           throw new RuntimeException("User already exists");
       }

       User user = User.builder()
               .username(registerRequest.getUsername())
               .password(passwordEncoder.encode(registerRequest.getPassword()))
               .email(registerRequest.getEmail())
               .build();

        System.out.println(user);

       userRepository.save(user);

    }

    public User loginUser(@Valid LoginRequest loginRequest) {

        Optional <User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Username or password is incorrect");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("username or Password is incorrect");
        }
        return user;
    }

    public User getById(UUID userId) {
        return userRepository.getUsersById(userId);
    }
}
