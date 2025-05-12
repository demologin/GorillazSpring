package com.javarush.lesson18.service;

import com.javarush.lesson18.repository.Repo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final Repo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.getUserByLogin(username)
                .map(u ->
                        org.springframework.security.core.userdetails.User
                                .withUsername(u.getLogin())
                                .password(u.getPassword())
                                .roles(u.getRole().getAuthority())
                                .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
