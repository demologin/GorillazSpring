package com.javarush.khmelov.lesson07.service;

import com.javarush.khmelov.lesson07.model.User;
import com.javarush.khmelov.lesson07.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<User> findUser(String loginStart, String roleFragment){
        return userRepository
                .findUser(loginStart,roleFragment)
                .stream()
                .toList();
    };

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
