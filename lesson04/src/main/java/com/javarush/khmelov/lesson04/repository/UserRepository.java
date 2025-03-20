package com.javarush.khmelov.lesson04.repository;

import com.javarush.khmelov.lesson04.config.SessionCreator;
import com.javarush.khmelov.lesson04.model.User;
import com.javarush.khmelov.lesson04.processor.annotation.TimeLog;
import com.javarush.khmelov.lesson04.processor.annotation.Tx;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@TimeLog
@Tx
public class UserRepository {

    private final SessionCreator sessionCreator;

    public Optional<User> getUserByIdFromRepo(Long id) {
        Session session = sessionCreator.getSession();
        return Optional.of(session.get(User.class, id));
    }
}
