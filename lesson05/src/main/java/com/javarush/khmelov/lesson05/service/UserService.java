package com.javarush.khmelov.lesson05.service;

import com.javarush.khmelov.lesson05.model.User;
import com.javarush.khmelov.lesson05.processor.annotation.TimeLog;
import com.javarush.khmelov.lesson05.processor.annotation.Tx;
import com.javarush.khmelov.lesson05.repository.UserRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ToString
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Tx
    @TimeLog
    public User getUserById(Long id) {
        Optional<User> userById = userRepository.getUserByIdFromRepo(id);
        return userById.orElseThrow();
    }


    @Override
    public String toString() {
        return "UserService{" +
               "demoRepository=" + userRepository +
               '}';
    }
}
