package com.javarush.khmelov.lesson06.service;

import com.javarush.khmelov.lesson06.config.AppConfig;
import com.javarush.khmelov.lesson06.config.OnService;
import com.javarush.khmelov.lesson06.model.User;
import com.javarush.khmelov.lesson06.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public User getUserById(Long id) {
        Optional<User> userOptional=userRepository.findById(id);
        return userOptional.orElseThrow();
    }


}
