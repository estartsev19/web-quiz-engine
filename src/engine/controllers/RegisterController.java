package engine.controllers;


import engine.entities.User;
import engine.repositories.UserRepository;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public void doRegister(@Valid @RequestBody User userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already taken");
        }
        User user = new User();
        user.setPassword(encodedPassword);
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
    }

}
