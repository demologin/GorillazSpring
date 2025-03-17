package com.javarush.khmelov.lesson03.service;

import com.javarush.khmelov.lesson03.model.User;
import com.javarush.khmelov.lesson03.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User getUserById(Long id) {
        Optional<User> userById = userRepository.getUserById(id);
        return userById.orElseThrow();
    }


    @Override
    public String toString() {
        return "UserService{" +
               "demoRepository=" + userRepository +
               '}';
    }
}
