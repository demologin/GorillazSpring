package com.javarush.khmelov.lesson05.repository;

import com.javarush.khmelov.lesson05.config.SessionCreator;
import com.javarush.khmelov.lesson05.model.User;
import com.javarush.khmelov.lesson05.processor.annotation.TimeLog;
import com.javarush.khmelov.lesson05.processor.annotation.Tx;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@TimeLog
@Tx
@ToString
public class UserRepository {

    private final SessionCreator sessionCreator;

    public Optional<User> getUserByIdFromRepo(Long id) {
        Session session = sessionCreator.getSession();
        return Optional.of(session.get(User.class, id));
    }
}
